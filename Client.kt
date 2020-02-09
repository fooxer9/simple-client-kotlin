package client_lab1

import java.io.OutputStream
import java.net.Socket
import java.nio.charset.Charset
import java.util.*


const val PORT = 12345

fun main(){
    val client = Client ("localhost", PORT)
    client.run()

}

class Client (address:String, port: Int) : Thread() {
    private val connection: Socket = Socket(address, port)

    init {
        println("Connected to server at $address on port $port")
    }
    private var connected = true
    private val reader: Scanner = Scanner (connection.getInputStream())
    private var writer: OutputStream = connection.getOutputStream()

   override fun run() {

       while (connected) {
           read()
           val input = readLine() ?: ""
           println(input)
           if ("exit" in input) {
               connected = false
               reader.close()
               connection.close()
           } else {
               write(input)
           }
           read()
       }
    }
    private fun write(message: String) {
        writer.write((message + '\n').toByteArray(Charset.defaultCharset()))
    }

    private fun read() {
            println(reader.nextLine())
    }
}
