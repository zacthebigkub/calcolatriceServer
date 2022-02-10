import java.io.*;
import java.net.Socket;

import java.util.Scanner;

public class Client {
    public static void main(String[] args){

        Socket socket = null;//Creating a socket with value null
        InputStreamReader inputStreamReader = null;//Creating an inputStreamReader with value null
        OutputStreamWriter outputStreamWriter = null;//Creating an outputStreamWriter with value null
        BufferedReader bufferedReader = null;//Creating a bufferedReader with value null
        BufferedWriter bufferedWriter = null;//Creating a bufferedWriter with value null

        try {
            socket = new Socket("localhost", 6789);//OPENING A SOCKET WITH THE SERVER WHICH HAS THE IP OF THE FIRST PARAMETER AND LISTENS WITH THE PORT ON THE SECOND PARAMETER
            System.out.println("Connection Estabilished with localhost at port " + socket.getPort());

            inputStreamReader = new InputStreamReader(socket.getInputStream());//Initialize inputStreaamReader passing down the InputStream of the socket
            outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());//Initialize outputStreamWriter passing down the OutputStream of the socket
            bufferedReader = new BufferedReader(inputStreamReader); //Creare a bufferedReader(it works with block of bytes) with the InputStreamReader object
            bufferedWriter = new BufferedWriter(outputStreamWriter); //Creare a bufferedWriter(**) with the outputStreamWriter object

            Scanner tastiera = new Scanner(System.in); //Create an object Scanner to handle future keyboard inputs.




//////////////////////////////////////////////////////////////////
            String msg; //Create a variable to store the message that you will send

            do {
                msg = tastiera.nextLine(); //Using the class Scanner from java.util.Scanner to get an input from the keyboard
                bufferedWriter.write(msg); //Writing a message into the buffer
                bufferedWriter.newLine(); //adding a new line since the command (Scanner)objectName.nextLine() doesn't it on its own
                bufferedWriter.flush(); //emptying the bufferedWriter and sending the messages to the server
                System.out.println("Server: " + bufferedReader.readLine()); //Read the message from the server using a bufferedReader
            } while (!msg.equalsIgnoreCase("disconnect")); //Keep doing this until the message that the client write is disconnect
////////////////////////////////////////////////////////////////

        } catch (IOException e) {
            e.printStackTrace();
        } finally { //After the try has being executed correctly finally the code is going to close the socket,inputStreamer,outputStreamer...
                    //It will close them only if they are not null (They are null if an IO error has occurred so you they didn't open from the beginning)
            try {
                if (socket != null) socket.close();
                if (inputStreamReader != null) inputStreamReader.close();
                if (outputStreamWriter != null) outputStreamWriter.close();
                if (bufferedReader != null) bufferedReader.close();
                if (bufferedWriter != null) bufferedWriter.close();
            } catch (IOException e){
                    e.printStackTrace();
                }//END CATCH
        }//END FINALLY
    }//END OF THE MAIN FUNCTION
}//END OF THE CLASS Client
