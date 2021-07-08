import java.io.FileNotFoundException;
import java.util.Scanner;
import java.awt.HeadlessException;
import javax.swing.JOptionPane;

//Felipe Leite Kogus
//RA: 771006

public class Gerenciador {

    public static void main(String[] args) {

        System.out.printf("\n\n\n\n--------------------------BEM-VINDO AO XADREZ----------------------------------\n\n");

        int jogoSalvo;
        Scanner scan = new Scanner(System.in);
        boolean salvar = false;
        Jogo j;

        try{
            jogoSalvo = JOptionPane.showConfirmDialog(null, "Deseja carregar um jogo salvo?", null,
                JOptionPane.YES_NO_OPTION);
        //atribui 0 caso o usuario clique em sim e 1 caso clique em nao. Caso
        //feche a janela de pergunta, atribui -1
        } catch(HeadlessException e){
            System.out.printf("Deseja carregar um jogo salvo? (0 = Sim, 1 = Não) ");
            jogoSalvo = scan.nextInt();
        }

        if (jogoSalvo == 0) {
            j = new Jogo(true);
            j.iniciarJogo();
        }
        else {
            j = new Jogo(false);
            j.iniciarJogo();
        }

        while(!j.t.fim && !salvar){
            try {
                salvar = j.jogada();//repete as jogadas enquanto o usuario nao desejar salvar ou enquanto nao acabar o jogo
            } catch (FileNotFoundException e) {
                System.out.println("Ocorreu um erro!");
            }
        }

        if(salvar) System.out.println("Seu jogo sera salvo!");
        scan.close();

    }
   

}

