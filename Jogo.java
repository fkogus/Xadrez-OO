
//Felipe Leite Kogus
//RA: 771006

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class Jogo {

    protected Tabuleiro t;
    private String status; //estado em que o jogo se encontra
    private char vez; //char 'P' ou 'B' representando de quem é a vez
    private Jogador j1, j2; // j1 = brancas e j2 = pretas
    private Peca[] peca = new Peca[32];
    private Scanner in = new Scanner(System.in);
    private Scanner arq; //Scanner para ler arquivo de save
    private File save = new File("save.txt"); //arquivo de save
    private boolean carregar, acabouOSave = true;
    //carregar: mostra se o usuario deseja carregar um jogo salvo
    //acabouOSave: utilizado quando esta carregando um arquivo, para determinar se 
    //o arquivo de save ja acabou


    /**
     * Cria um tabuleiro; se o usuario deseja carregar um jogo salvo, inicializa o scanner do
     * arquivo. Porem, se o arquivo nao for encontrado, mostra uma mensagem de erro e 
     * inicia um novo jogo
     * @param carregar Mostra se o usuario deseja carregar um jogo salvo
     */
    Jogo(boolean carregar) {

        t = new Tabuleiro();
        this.carregar = carregar;

        if(carregar){
            try {
                arq = new Scanner(save);
            } catch (FileNotFoundException e) {
                if(carregar) 
                    System.out.println("Ocorreu um erro ao recuperar o jogo salvo! Iniciando um novo jogo");

                this.carregar = !carregar;
            }
        }
        

    }

    /**
     * Funcao que inicializa e atribui as pecas aos jogadores. Tambem chama a funcao que distribui
     * as pecas no tabuleiro
     */
    public void iniciarJogo() {

        //JOGADOR 1:
        String nome;
        System.out.printf("Digite o nome do jogador das pecas brancas: ");
        if(!this.carregar){
            nome = this.in.nextLine(); //se nao estiver carregando um jogo, ler nome do teclado
        }
        else{
            this.acabouOSave = false;// se estiver carregando um save, a variavel acabouOSave é definida como false
            nome = this.arq.nextLine();//ler o nome do arquivo
        }
        j1 = new Jogador(nome, 'B');

        //-----------------------------------------------------------------------
        //JOGADOR 2:

        String nome2;
        System.out.printf("Digite o nome do jogador das pecas pretas: ");
        if(!this.carregar){
            nome2 = this.in.nextLine();
        }
        else{
            nome2 = this.arq.nextLine();
        }            
        j2 = new Jogador(nome2, 'P');

        //------------ESCRITA DE SAVE--------------------------
        if(!this.carregar){ //executado somente se nao estiver carregando um jogo
            try {

                FileWriter escritor = new FileWriter("save.txt");
                escritor.write(nome + "\n" + nome2);//escreve os nomes dos jogadores no arquivo
                escritor.close();

            } catch (IOException e) {
                System.out.println("Erro ao salvar configuracoes!");
                //Mensagem mostrada na tela caso nao consigo escrever os nomes no arquivo
            }
        }

        //-------------------------------------------------------

        //as primeiras 16 pecas sao do jogador 1 (brancas) e as outras sao do jogador 2 (pretas)

        for(int i = 0; i<16; i++){
            this.peca[i] = j1.peca[i];
        }

        int i, x;

        for(i=16, x=0; i<32; i++, x++){
            this.peca[i] = j2.peca[x];
        }

        t.inicializar(peca); //distribui as pecas no tabuleiro
        t.imprimeTabuleiro();

        this.status = "inicio";
        this.vez = 'B';

    }

    /**
     * Funcao que, com o auxilio de outras funcoes, recebe as coordenadas, chama o metodo de
     * checagem de movimento e, se estiver correto, realiza o movimento. Apos o movimento, tambem
     * verifica se o jogo esta em xeque ou xeque-mate.
     * @return Booleano que indica se o usuario deseja salvar o jogo
     * @throws FileNotFoundException
     */
    public boolean jogada() throws FileNotFoundException {

        char[] coord;
        char[] coord2;
        boolean xeque = false;

        if(this.status == "xeque") xeque = true;

        if(this.vez == 'B') System.out.printf("É a vez de %s\n", this.j1.getNome());
        else if(this.vez == 'P') System.out.printf("É a vez de %s\n", this.j2.getNome());

        //-------------------LEITURA DE COORDENADAS--------------------------

        coord = this.ler(true); //A leitura e "filtragem" é realizada em funcao separada
        if(coord[0] == '0' && coord[1] == '0') {
            return true; //Caso o usuario digite 00, sera escolhida a opcao de salvar o jogo
        }

        int linha = Character.getNumericValue(coord[0]); //Linha é recebida como int
        char coluna = coord[1]; //Coluna se mantem como char

       //O mesmo ocorre com as coordenadas de destino

        coord2 = this.ler(false);

        int linha2 = Character.getNumericValue(coord2[0]);
        char coluna2 = coord2[1];

        //--------------------FIM DA LEITURA---------------------------------

        //Chama a funcao principal de checagem de movimento
        if(t.checaMovimento(linha, coluna, linha2, coluna2, this.vez)){
            t.mover(linha, coluna, linha2, coluna2);//Se o movimento estiver correto, realiza o mov
            xeque = this.xeque(); //checa se o jogo esta em xeque
            //Muda a vez:
            if(this.vez == 'B') this.vez = 'P';
            else if(this.vez == 'P') this.vez = 'B';

            //----------------ESCRITA DE SAVE-------------------------------
                if(this.acabouOSave){ 
                    //Se o arquivo de save ja acabou, comeca a escrever no arquivo
                    try {
                        FileWriter escritor = new FileWriter("save.txt", true);
                        String temp = new String(coord);
                        String temp2 = new String(coord2);
                        escritor.write("\n" + temp + "\n" + temp2);//Escreve as jogadas no arquivo
                        escritor.close();
                    } catch (IOException e) {
                        System.out.println("Erro ao salvar configuracoes!");
                        //Se nao conseguiu escrever no arquivo, mostra mensagem de erro
                    }
                }
            //------------------------------------------------------------------
            
        }
        else{
            System.out.printf("\nMOVIMENTO INVALIDO!\n");
            //Se movimento nao é valido pelas regras de xadrez
        }

        //--------------------FIM DA CHECAGEM DE MOVIMENTO---------------------

        if(xeque) System.out.println("O rei esta em xeque!");

        if(this.t.fim) {
            System.out.println("FIM DE JOGO!");
            if(arq != null) arq.close(); //Se o scanner de arquivo esta aberto, fecha
            save.delete(); //Deleta o arquivo de save, pois o jogo acabou
        }

        if(!this.t.fim) t.imprimeTabuleiro();

        return false;

    }

    /**
     * Funcao que verifica se o jogo esta em xeque. Para isso, varre o tabuleiro e checa
     * todas as pecas do jogador da vez atual e verifica se cada uma pode chegar ate o 
     * rei inimigo
     * @return Boolean indicando se o jogo esta em cheque
     */
    private boolean xeque(){

        Posicao rei = this.acharReiInimigo();
        int linha2 = rei.getLinha();
        char coluna2 = rei.getColuna();

        for(int i=1; i<9; i++){
            for(int j=1; j<9; j++){
                if(this.t.pos[i][j].isOcupada()){
                    if(this.t.pos[i][j].peca.getCor() == this.vez){
                        if(this.t.checaMovimento(i, (char)(j+64), linha2, coluna2, vez)){
                            //System.out.println("O rei esta em xeque!");
                            this.status = "xeque";
                            return true;
                        }
                    }
                }
            }
        }

        this.status = "meio"; //Muda o status do jogo para "meio", pois uma jogada ja foi feita
        return false;

    }

    /**
     * Varre o tabuleiro inteiro e procura o Rei inimigo
     * @return Elemento da classe Posicao onde esta localizado o Rei inimigo
     */
    private Posicao acharReiInimigo(){
        for(int i=1; i<9; i++){
            for(int j=1; j<9; j++){

                if(this.t.pos[i][j].isOcupada()){

                    if(this.t.pos[i][j].peca.getClass().getName() == "Rei" && 
                    this.t.pos[i][j].peca.getCor() != this.vez){
                        return this.t.pos[i][j];//posicao do rei inimigo
                    }
                }

            }
        }

        return this.t.pos[0][0]; //erro, nunca acontecera

    }

    /**
     * Funcao que faz a leitura e filtro das coordenadas
     * @param posInicial Boolean que indica se a coordenada se refere à posicao de 
     * origem (true) ou destino (false)
     * @return Retorna um vetor de char com as coordenadas lidas corretamente
     */
    private char[] ler(boolean posInicial){

        String entrada;
        char[] retorno = {'A', 'B'};
        boolean lerDnv = false;
        int num = 0, letra = 1;
        int qtNum = 0, qtLetras = 0;

        do{
            
            //Reinicializacao de variaveis
            entrada = null;
            lerDnv = false;
            qtNum = 0;
            qtLetras = 0;


            if(posInicial){
                System.out.println("Digite 00 para sair e salvar o jogo");
                System.out.printf("Digite as coordenadas da peca que deseja mover: ");
            }
            else{
                System.out.printf("Digite as coordenadas da posicao em que deseja mover a peca: ");
            }

            if(this.arq != null && this.carregar){
                if(this.arq.hasNextLine()){ //Se ainda ha linhas no arquivo para ler
                    entrada = this.arq.nextLine(); //Ler coordenadas do save
                }
                else{
                    this.acabouOSave = true;//Caso nao haja linhas, o arquivo de save acabou
                    entrada = in.nextLine();
                }
            }
            else
                entrada = in.nextLine(); //Se nao for para carregar, ler diretamente do teclado
        
            if(entrada.equals("00")){
                retorno[0] = '0';
                retorno[1] = '0';
                return retorno; //Caso o usuario digite 00, sera escolhida a opcao de salvar o jogo
            }

            if(entrada.length() != 2) { //A entrada deve ter somente 2 caracteres (linha e coluna)
                System.out.println("Entrada invalida, nao utilize espacos ou outros caracteres alem de letras e numeros");
                lerDnv = true;
            }

            for(int i=0; i<entrada.length() && !lerDnv; i++){ //Entrada deve ter somente letras e numeros
                if(!Character.isLetterOrDigit(entrada.charAt(i))){
                    System.out.println("Entrada invalida, nao utilize espacos ou outros caracteres alem de letras e numeros");
                    lerDnv = true;
                }
            }

            for(int i=0; i<entrada.length() && !lerDnv; i++){
                if(Character.isDigit(entrada.charAt(i))){
                    qtNum++;
                }
                else if(Character.isLetter(entrada.charAt(i))){
                    qtLetras++;
                }
                //Entrada deve conter somente uma letra e um numero
            }

            if((qtNum != 1 || qtLetras != 1) && !lerDnv){
                System.out.println("Entrada invalida, nao utilize espacos ou outros caracteres alem de letras e numeros");
                lerDnv = true;
                //Entrada deve conter somente uma letra e um numero
            }

            for(int i=0; i<2 && !lerDnv; i++){
                if(Character.isDigit(entrada.charAt(i))) {
                    num = i; //pega posicao da string onde esta o numero
                }
                else if(Character.isLetter(entrada.charAt(i))) letra = i; 
                //pega posicao da string onde esta a letra
            }

        }while(entrada.length() != 2 || lerDnv);

        retorno[0] = entrada.charAt(num);
        retorno[1] = entrada.charAt(letra);

        return retorno;

    }


}
