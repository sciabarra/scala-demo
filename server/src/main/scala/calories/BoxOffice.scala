package calories

import java.io.{FileWriter, FileReader, File}
import java.util.{Properties, Random}

/**
  * Manage tickets, issuing and cheking validity
  */
object BoxOffice {
  var roleByTicket = Map.empty[String, String]

  // utils
  def file(dir: File, file: String) = new java.io.File(dir, file)

  def file(dir: String) = new java.io.File(dir)

  // generate tickets
  val rnd = new Random()

  def genTicket = rnd.nextLong().toHexString


  // check the role - empty string if no role
  def checkRole(ticket: String): String = roleByTicket.getOrElse(ticket, "")

  /**
    * Check the user in the "data base" then issue a ticket
    *
    * @param user to log in
    * @param password to cjeck
    * @return
    */
  def issue(user: String, password: String): LoggedUser = {
    // load user data
    val userFolder = file(file("data"), user)
    val prpFile = file(userFolder, "user.properties")
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
  def invalidate(ticket: String) = {
    println(s"invalidate: ${ticket}")
    roleByTicket -= ticket
    println(roleByTicket)
    LoggedUser(Left("logged out"))
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
      LoggedUser(Left("Username must be all letter or digits"))
    } else {
      val userFolder = file(file("data"), user)
      val prpFile = file(userFolder, "user.properties")
      userFolder.mkdirs
      val prp = new Properties
      prp.setProperty("name", name)
      prp.setProperty("role", "user")
      prp.setProperty("password", password)
      prp.store(new FileWriter(prpFile), "# created by Calories Server")
      issue(user, password)
    }
  }

  /**
    * Removing all data - carefully !
    * @param user
    */
  def cleanup(user: String): String = {
    println(s"cleanup: ${user}")
    val userFolder = file(file("data"), user)
    val prpFile = file(userFolder, "user.properties")
    if (userFolder.exists && prpFile.exists) {
      for (file <- userFolder.listFiles())
        if (file.getName.endsWith(".json"))
          file.delete()
      prpFile.delete
      userFolder.delete
      s"deleted ${user}"
    } else {
      s"not found ${user}"
    }
  }
}
