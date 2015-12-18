package calories

import java.io.{FileWriter, FileReader, File}
import java.util.{Properties, Random}

import com.typesafe.scalalogging.LazyLogging

/**
  * Manage tickets, issuing and cheking validity
  */
object BoxOffice extends LazyLogging {

  var userByTicket = Map.empty[Int, LoggedUser]

  // utils
  def file(dir: File, file: String) = new java.io.File(dir, file)

  def file(dir: String) = new java.io.File(dir)

  // generate tickets
  val rnd = new Random()

  def genTicket = Math.abs(rnd.nextInt())

  /**
    * Check if the user with the given ticket has the requested role
    * @param ticket
    * @param role
    * @return
    */
  def checkRole(ticket: Int, role: String) = userByTicket.get(ticket).map(_.role == role).getOrElse(false)

  val noSuchUser = LoggedUser(Left("No such user!"))

  /**
    * get the user by ticket
    */
  def getUser(ticket: Int) = userByTicket.getOrElse(ticket, noSuchUser)

  /**
    * Check the user in the "data base" then issue a ticket
    *
    * @param user to log in
    * @param password to check
    * @return
    */
  def issue(user: String, password: String): LoggedUser = {
    logger.debug(s"issue: ${user}/${password}")
    // load user data
    val userFolder = file(file("data"), user)
    val prpFile = file(userFolder, "user.properties")
    logger.debug(s"${prpFile.getAbsolutePath}")
    if (userFolder.exists && prpFile.exists) {
      val prp = new Properties
      prp.load(new FileReader(prpFile))
      // check password
      if (prp.getProperty("password") == password) {
        // generate ticket and record it
        val ticket = genTicket
        val role = prp.getProperty("role")
        val loggedUser = LoggedUser(Right(ticket),
          name = prp.getProperty("name"),
          username = user,
          role = role,
          calories = prp.getProperty("calories").toInt)
        userByTicket = userByTicket + (ticket -> loggedUser)
        logger.debug(s"${userByTicket}")
        loggedUser
      } else {
        LoggedUser(Left("Username or password incorrect!"))
      }
    } else {
      LoggedUser(Left("No such user. Please register!"))
    }
  }

  /**
    * Invalidate a ticket
    *
    * @param ticket
    * @return
    */
  def invalidate(ticket: Int) = {
    logger.debug(s"invalidate: ${ticket}")
    if (ticket == 0) {
      LoggedUser(Left("Please login or register."))
    } else if (userByTicket.get(ticket).isEmpty) {
      LoggedUser(Left("No such ticket!"))
    } else {
      userByTicket -= ticket
      LoggedUser(Left("Logged out."))
    }
  }

  /**
    * Register a new user storing it in
    *
    * @param req new user registration request
    * @return the logged user (or a logged user with an error)
    */
  def register(req: Register) = {
    logger.debug(s"register: ${req}")

    if (!req.username.forall(_.isLetterOrDigit)) {
      LoggedUser(Left("Username must be all letter or digits!"))
    } else {
      val userFolder = file(file("data"), req.username)
      val prpFile = file(userFolder, "user.properties")
      if (prpFile.exists()) {
        LoggedUser(Left("Username already exists!"))
      } else {
        userFolder.mkdirs
        val prp = new Properties
        prp.setProperty("name", req.name)
        prp.setProperty("role", "user")
        prp.setProperty("password", req.password)
        prp.setProperty("calories", req.calories.toString)
        prp.store(new FileWriter(prpFile), "# created by Calories Server")
        issue(req.username, req.password)
      }
    }
  }

  /**
    * Removing all data - carefully !
    * @param user
    */
  def cleanup(user: String): LoggedUser = {
    logger.debug(s"cleanup: ${user}")
    val userFolder = file(file("data"), user)
    val prpFile = file(userFolder, "user.properties")
    if (userFolder.exists && prpFile.exists) {
      for (file <- userFolder.listFiles())
        if (file.getName.endsWith(".json"))
          file.delete()
      prpFile.delete
      userFolder.delete
      LoggedUser(Left(s"deleted ${user}"))
    } else {
      LoggedUser(Left(s"not found ${user}"))
    }
  }
}
