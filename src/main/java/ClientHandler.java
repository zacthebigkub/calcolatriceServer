import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler extends Thread {

    Socket socket = null;
    InputStreamReader inputStreamReader = null;
    OutputStreamWriter outputStreamWriter = null;
    BufferedReader bufferedReader = null;
    BufferedWriter bufferedWriter = null;

    public ClientHandler() {

    }


    public ClientHandler(Socket socket) {
        try {
            this.inputStreamReader = new InputStreamReader(socket.getInputStream());//Initialize inputStreaamReader passing down the InputStream of the socket
            this.outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());//Initialize outputStreamWriter passing down the OutputStream of the socket
            this.bufferedReader = new BufferedReader(inputStreamReader); //Creare a bufferedReader(it works with block of bytes) with the InputStreamReader object
            this.bufferedWriter = new BufferedWriter(outputStreamWriter); //Creare a bufferedWriter(**) with the outputStreamWriter object
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void run() {
        super.run();

        try {
            String msgReceived; //Create a variable to store the message that the client sent to you
            do {
                msgReceived = bufferedReader.readLine(); //Inserting into msgReceived the textStream from the Client (It will insert what the client will write)

                if (msgReceived.equalsIgnoreCase("disconnect")) { // If the message received is disconnect the cycle will end
                    System.out.println("Client is disconnecting...");
                    break; //it exits the do-while loop
                }

                System.out.println("Client: " + msgReceived);


                bufferedWriter.write(calculate(equationDivisor(msgReceived)));  //Writing a message into the buffer
                bufferedWriter.newLine(); //adding a new line since the command (Scanner)objectName.nextLine() doesn't it on its own
                bufferedWriter.flush(); //emptying the bufferedWriter and sending the messages to the server

            } while (true);

            //Close every connection
            socket.close();
            inputStreamReader.close();
            outputStreamWriter.close();
            bufferedReader.close();
            bufferedWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String calculate(ArrayList<String> equations) {
        int i = 1;

        while (equations.get(0).length() > 0) {

            switch (equations.get(0).charAt(i - 1)) {
                case '*':
                    equations.set(i, Double.toString(Double.parseDouble(equations.get(i)) * Double.parseDouble(equations.get(i + 1)))); //SET THE FRESH CALCULATED OPERATION AT I
                    equations.remove(i + 1); //REMOVE THE PART OF THE EQUATIONS THAT DOESN'T EXIST ANYMORE
                    equations.set(0, equations.get(0).substring(0, i - 1) + equations.get(0).substring(i)); //REMOVE THE JUST CALCULATED OPERATOR
                    i--;

                    break;
                case '/':
                    equations.set(i, Double.toString(Double.parseDouble(equations.get(i)) / Double.parseDouble(equations.get(i + 1)))); //SET THE FRESH CALCULATED OPERATION AT I
                    equations.remove(i + 1); //REMOVE THE PART OF THE EQUATIONS THAT DOESN'T EXIST ANYMORE
                    equations.set(0, equations.get(0).substring(0, i - 1) + equations.get(0).substring(i)); //REMOVE THE JUST CALCULATED OPERATOR
                    i--;

                    break;
                case '+':
                    if (equations.get(0).contains("*") || equations.get(0).contains("/")) break;

                    equations.set(i, Double.toString(Double.parseDouble(equations.get(i)) + Double.parseDouble(equations.get(i + 1)))); //SET THE FRESH CALCULATED OPERATION AT I
                    equations.remove(i + 1); //REMOVE THE PART OF THE EQUATIONS THAT DOESN'T EXIST ANYMORE
                    equations.set(0, equations.get(0).substring(0, i - 1) + equations.get(0).substring(i)); //REMOVE THE JUST CALCULATED OPERATOR
                    i--;

                    break;
                case '-':
                    if (equations.get(0).contains("*") || equations.get(0).contains("/")) break;

                    equations.set(i, Double.toString(Double.parseDouble(equations.get(i)) - Double.parseDouble(equations.get(i + 1)))); //SET THE FRESH CALCULATED OPERATION AT I
                    equations.remove(i + 1); //REMOVE THE PART OF THE EQUATIONS THAT DOESN'T EXIST ANYMORE
                    equations.set(0, equations.get(0).substring(0, i - 1) + equations.get(0).substring(i)); //REMOVE THE JUST CALCULATED OPERATOR
                    i--;
                    break;
                default:
                    return "Syntax Error";
            }

            if (i > equations.get(0).length() - 1) i = 1;
            else i++;

        }
        ;

        switch (equations.get(1)) {
            case "Infinity":
                return "Impossibile dividere per 0";

            default:
                return equations.get(1);
        }

    }


    private ArrayList<String> equationDivisor(String msgReceived) {
        ArrayList<String> equations = new ArrayList<>();
        equations.add("");

        int lastIndex = 0;
        for (int i = 0; i < msgReceived.length(); i++) {
            if (msgReceived.charAt(i) != '.' && msgReceived.charAt(i) != '1' && msgReceived.charAt(i) != '2' && msgReceived.charAt(i) != '3' && msgReceived.charAt(i) != '4' && msgReceived.charAt(i) != '5' && msgReceived.charAt(i) != '6' && msgReceived.charAt(i) != '7' && msgReceived.charAt(i) != '8' && msgReceived.charAt(i) != '9' && msgReceived.charAt(i) != '0') {
                if (i == lastIndex) {
                    equations.clear();
                    equations.add("Impossible");
                    break;
                }
                equations.add(msgReceived.substring(lastIndex, i));
                System.out.println(equations.get(equations.size()-1));
                equations.set(0, equations.get(0) + msgReceived.charAt(i));
                lastIndex = i + 1;
            }
        }
        equations.add(msgReceived.substring(lastIndex));

        return equations;
    }

}