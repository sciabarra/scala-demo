package calories

import java.io.{FileWriter, FileReader, File}
import java.util.{Properties, Random}

import com.typesafe.scalalogging.LazyLogging

/**
  * Manage tickets, issuing and cheking validity
  */
object BoxOffice extends LazyLogging {

  var roleByTicket = Map.empty[Int, String]

  // utils
  def file(dir: File, file: String) = new java.io.File(dir, file)

  def file(dir: String) = new java.io.File(dir)

  // generate tickets
  val rnd = new Random()

  def genTicket = Math.abs(rnd.nextInt())

  // check the role - empty string if no role
  def checkRole(ticket: Int): String = roleByTicket.getOrElse(ticket, "")

  /**
    * Check the user in the "data base" then issue a ticket
    *
    * @param user to log in
    * @param password to cjeck
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
        roleByTicket += (ticket -> "user")
        LoggedUser(Right(ticket),
          name = prp.getProperty("name"),
          username = user,
          role = role)
      } else {
        LoggedUser(Left("username or password incorrect"))
      }
    } else {
      LoggedUser(Left("no such user - please register"))
    }
  }

  /**
    * Invalidate a ticket
    *
    * @param ticket
    * @return
    */
  def invalidate(ticket: Int) = {
    println(s"invalidate: ${ticket}")
    if (checkRole(ticket).isEmpty)
      LoggedUser(Left("no such ticket"))
    else {
      roleByTicket -= ticket
      println(roleByTicket)
      LoggedUser(Left("logged out"))
    }
  }

  /**
    * Register a new user storing it in
    *
    * @param name of the new user
    * @param user for login
    * @param password for login
    * @return
    */
  def register(name: String, user: String, password: String) = {
    println(s"register: ${name}/${password}")
    if (!user.forall(_.isLetterOrDigit)) {
      LoggedUser(Left("username must be all letter or digits"))
    } else {
      val userFolder = file(file("data"), user)
      val prpFile = file(userFolder, "user.properties")
      if(prpFile.exists()) {
        LoggedUser(Left("username already exists"))
      }  else {
        userFolder.mkdirs
        val prp = new Properties
        prp.setProperty("name", name)
        prp.setProperty("role", "user")
        prp.setProperty("password", password)
        prp.store(new FileWriter(prpFile), "# created by Calories Server")
        issue(user, password)
      }
    }
  }

  /**
    * Removing all data - carefully !
    * @param user
    */
  def cleanup(user: String): LoggedUser = {
    println(s"cleanup: ${user}")
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
