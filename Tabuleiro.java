//Felipe Leite Kogus
//RA: 771006

public class Tabuleiro {
    
    protected Posicao[][] pos = new Posicao[9][9]; //Uma linha e uma coluna a mais para mostrar as letras/numeros
    protected boolean fim = false;

    //Inicializa as posicoes do tabuleiro
    Tabuleiro(){
        for (int i=0; i < 9; i++) {
            for(int j=0; j<9; j++){
                this.pos[i][j] = new Posicao(i, j); //inicializa todas as posicoes
            }
        }
    }

    /**
     * Funcao que mostra o tabuleiro na tela
     */
    public void imprimeTabuleiro(){
        System.out.printf("\n");
        for(int i=0; i<9; i++){
            for(int j=0; j<9; j++){
                System.out.printf("%c ", this.pos[i][j].getSimbolo());
            }
            System.out.printf("\n");
        }

        System.out.printf("\n");

    }

    /**
     * Funcao que realiza o movimento, independente da checagem
     * @param linha1 Linha inicial
     * @param coluna1 Coluna inicial
     * @param linha2 Linha final
     * @param coluna2 Coluna final
     */
    public void mover(int linha1, char coluna1, int linha2, char coluna2){

        //caso as letras sejam minusculas, sao passadas para maiusculas
        coluna1 = java.lang.Character.toUpperCase(coluna1);
        coluna2 = java.lang.Character.toUpperCase(coluna2);

        int col1 = (char)(coluna1-64);
        int col2 = (char)(coluna2-64);

        //System.out.printf("Movendo um %s\n", nome);

        if(this.pos[linha2][col2].isOcupada()){
            this.fim = this.comer(linha2, col2);
        }

        //Realizacao do movimento:
        this.pos[linha2][col2].peca  =  this.pos[linha1][col1].peca;
        this.pos[linha2][col2].setSimbolo(this.pos[linha1][col1].getSimbolo());
        this.pos[linha1][col1].peca = null; 
        this.simboloVazio(linha1, col1);
        this.pos[linha1][col1].setOcupada(false);
        this.pos[linha2][col2].setOcupada(true);
        this.pos[linha2][col2].peca.setLinha(linha2);
        this.pos[linha2][col2].peca.setColuna(coluna2);
        

    }

    /**
     * Muda o simbolo quando a posicao esta vazia
     * @param linha Linha da posicao
     * @param coluna Coluna da posicao
     */
    private void simboloVazio (int linha, int coluna){
        if(linha % 2 == 0 && coluna % 2 != 0 && linha!=0 && coluna!=0){
            this.pos[linha][coluna].setCor('B');
            this.pos[linha][coluna].setSimbolo((char)9633); //quadrado vazio
        }
        else if(linha % 2 != 0 && coluna % 2 == 0 && linha!=0 && coluna!=0){
            this.pos[linha][coluna].setCor('B');
            this.pos[linha][coluna].setSimbolo((char)9633); //quadrado vazio
        }
        else if (linha!=0 && coluna!=0){
            this.pos[linha][coluna].setCor('P');
            this.pos[linha][coluna].setSimbolo((char)9632); //quadrado preto
        }

    }

    /**
     * Faz o movimento de captura de uma peca, mostrando uma mensagem na tela
     * @param linha Linha da peca capturada
     * @param coluna Coluna da peca capturada
     * @return Booleano que diz se o rei foi capturado e, consequentemente, o jogo terminou
     */
    private boolean comer(int linha, int coluna){
        String peca = this.pos[linha][coluna].peca.getClass().getName();
        if(peca != "Torre" && peca != "Dama" && peca != "Rei"){
            System.out.printf("\nUm %s foi capturado!\n", peca);
            this.pos[linha][coluna].peca = null;
            return false;
        }
        else if (peca != "Rei"){
            System.out.printf("Uma %s foi capturada!\n", peca);
            this.pos[linha][coluna].peca = null;
            return false;
        }
        else{
            System.out.println("XEQUE-MATE");
            this.pos[linha][coluna].peca = null;
            return true;
        }
        
    }

    /**@param linha1 linha inicial
     * @param coluna1 coluna inicial
     * @param linha2 linha final
     * @param coluna2 coluna final
     * @param cor cor das pecas do jogador da vez
     * @return boolean true se for possivel fazer o movimento e false caso contrario, faz checagem especial 
     * para o peao, caso queira capturar uma peca
     */
    public boolean checaMovimento(int linha1, char coluna1, int linha2, char coluna2, char cor){

        coluna1 = java.lang.Character.toUpperCase(coluna1);
        coluna2 = java.lang.Character.toUpperCase(coluna2);

        int col1 = (int)(coluna1-64);
        int col2 = (int)(coluna2-64);

        if(!this.checaMovPecas(linha1, coluna1, linha2, coluna2, cor)) return false;

        //-----------------CHECAGEM PARA O PEAO-----------------------------------

        else if(this.pos[linha1][col1].peca.getClass().getName() == "Peao"){
            
            if(col1 - col2 == 0 && linha2 - linha1 == 1 && this.pos[linha2][col2].isOcupada() && cor == 'B'){
                return false;
                //tentativa de capturar peca a frente
            }

            if(col1-col2 == 0 && linha2 - linha1 == -1 && this.pos[linha2][col2].isOcupada() && cor =='P'){
                return false;
                //tentativa de capturar peca a frente
            }

            if((Math.abs(col2 - col1)) == 1 && linha2 - linha1 == 1 && cor == 'B'){
                if(this.pos[linha2][col2].isOcupada()){
                    if(this.pos[linha2][col2].peca.getCor() != cor){
                        return true;
                        //Capturar peca de maneira correta
                    }
                    else return false;
                }
            }
            else if((Math.abs(col2 - col1)) == 1 && linha2 - linha1 == -1 && cor == 'P'){
                if(this.pos[linha2][col2].isOcupada()){
                    if(this.pos[linha2][col2].peca.getCor() != cor){
                        return true;
                        //Capturar peca de maneira correta
                    }
                    else return false;
                }
            }
        }

        //--------------------------------------------------------------------------------
    
        if(!this.pos[linha1][col1].peca.checaMovimento(linha1, coluna1, linha2, coluna2)) return false;
        else if(!this.checaMovLimites(linha1, coluna1, linha2, coluna2)) return false;
        else if(!this.checaMovCaminho(linha1, coluna1, linha2, coluna2)) return false;
        

        return true;
        
    }

    /**
     * Checa se o usuario esta mexendo uma posicao que tenha alguma peca e se essa peca pertence
     * a ele
     * @param linha1 Linha inicial
     * @param coluna1 Coluna inicial
     * @param linha2 Linha final
     * @param coluna2 Coluna final
     * @param cor cor das pecas do jogador atual
     * @return Booleano que diz se o movimento é valido
     */
    private boolean checaMovPecas(int linha1, char coluna1, int linha2, char coluna2, char cor){
        int col1 = (int)(coluna1-64);
        int col2 = (int)(coluna2-64);

        if(!this.pos[linha1][col1].isOcupada()) return false;
        else if(!(this.pos[linha1][col1].peca.getCor() == cor)) return false;
        else if(this.pos[linha2][col2].isOcupada()){
             if (this.pos[linha2][col2].peca.getCor() == cor) return false;
        }
        
        //System.out.printf("Passou na checagem das pecas\n");
        return true;
        

    }

    /**
     * Checa se o movimento esta dentro dos limites do tabuleiro
     * @param linha1 Linha inicial
     * @param coluna1 Coluna inicial
     * @param linha2 Linha final
     * @param coluna2 Coluna final
     * @return Booleano que diz se o movimento é valido
     */
    private boolean checaMovLimites(int linha1, char coluna1, int linha2, char coluna2){
        int j1 = (int) coluna1-64;
        int j2 = (int) coluna2-64;

        if(j1 <= 0 || j1 >= 9 || j2 <= 0 || j2 >= 9){
            //System.out.println("Movimento inválido!");
            return false;
        }
        else if(linha1 <= 0 || linha1 >= 9 || linha2 <= 0 || linha2 >= 9){
            //System.out.println("Movimento inválido!");
            return false;
        }
        else {
            //System.out.println("Passou na checagem dos limites");
            return true;
        }
    }

    /**
     * Checa se o caminho esta livre para o movimento ser realizado
     * @param linha1 Linha inicial
     * @param coluna1 Coluna inicial
     * @param linha2 Linha final
     * @param coluna2 Coluna final
     * @return Booleano que diz se o movimento é valido
     */
    private boolean checaMovCaminho(int linha1, char coluna1, int linha2, char coluna2){

        if(this.pos[linha1][(int)(coluna1-64)].peca.getClass().getName() == "Cavalo"){
            return true;
        }
 
        if(linha1 == linha2 && coluna1 != coluna2){ //movimento na mesma linha

            if(coluna1<coluna2){
                char x;
                for(x = coluna1; x<coluna2; x++){
                    int a = (int)x; //typecasting
                    if(this.pos[linha1][a-64].isOcupada() && (a-64) != (int)(coluna1-64)){
                        return false;
                    }
                }
            }

            if(coluna1 > coluna2){
                char x;
                for(x=coluna1; x>coluna2; x--){
                    int a = (int)x; //typecasting
                    if(this.pos[linha1][a-64].isOcupada() && (a-64) != (int)(coluna1-64)){
                        return false;
                    }
                }
            }
        }

        else if(coluna1 == coluna2 && linha1 != linha2){ //movimento na mesma coluna
            int col = (int)(coluna1-64); //typecasting para trabalhar com matriz de posicoes

            if(linha1 < linha2){
                for(int i=linha1+1; i<linha2; i++){
                    if(this.pos[i][col].isOcupada()){
                        return false;
                    }
                }
            }

            if(linha2 < linha1){
                for(int i=linha1-1; i>linha2; i--){
                    if(this.pos[i][col].isOcupada()){
                        return false;
                    }
                }
            }

        }

        else if(linha1 != linha2 && coluna1 != coluna2){ //bispo, rainha ou rei na diagonal

            int col1 = (int)(coluna1-64);
            int col2 = (int)(coluna2-64); //typecasting para o vetor
            int i = linha1;
            
            if(col1 < col2 && linha2 < linha1){
                for(col1 = col1+1, i = i-1; col1 < col2; col1++, i--){
                    if(this.pos[i][col1].isOcupada()){
                       // System.out.printf("invalido pq a  pos[%d][%d] ta ocupada\n\n\n", i, col1);
                        return false;
                    }
                }
            }

            else if(col1 < col2 && linha2 > linha1){
                for(col1 = col1+1, i = i+1; col1 < col2; col1++, i++){
                    if(this.pos[i][col1].isOcupada()){
                        //System.out.printf("invalido pq a pos[%d][%d] ta ocupada\n\n\n", i, col1);
                        return false;
                    }
                }
            }

            else if(col1 > col2 && linha1 < linha2){
                for(col1 = col1-1, i = i+1; col1 > col2; col1--, i++){
                    if(this.pos[i][col1].isOcupada()){
                        //System.out.printf("invalido pq a pos[%d][%d] ta ocupada\n\n\n", i, col1);
                        return false;
                    }
                }
            }

            else if(col1 > col2 && linha1 > linha2){
                for(col1 = col1-1, i = i-1; col1 > col2; col1--, i--){
                    if(this.pos[i][col1].isOcupada()){
                        //System.out.printf("invalido pq a pos[%d][%d] ta ocupada\n\n\n", i, col1);
                        return false;
                    }
                }
            }

        }

        //System.out.println("Passou na checagem dos caminhos");
        return true;

    }

    /**
     * Distribui as pecas em suas posicoes iniciais no tabuleiro
     * @param peca Vetor com as pecas dos jogadores
     */
    public void inicializar(Peca peca[]){
        //linha e coluna 0 serao ignoradas pois sao as que desenham as letras e numeros
        int linha, col, i;

        //Pecas brancas:
        for(linha = 2, col = 1, i=0; col<=8; col++, i++){
            this.pos[linha][col].peca = peca[i];
            this.pos[linha][col].setOcupada(true);
            this.pos[linha][col].setSimbolo(this.pos[linha][col].peca.desenho());
        }
        for(linha=1, col = 1, i=8; col<=8 && i<16; col++, i++){
            this.pos[linha][col].peca = peca[i];
            this.pos[linha][col].setOcupada(true);
            this.pos[linha][col].setSimbolo(this.pos[linha][col].peca.desenho());
        }
        //---------------------------------------------------------------------------

        //Pecas pretas:
        for(linha = 7, col=1, i=16; col<=8; col++, i++){
            this.pos[linha][col].peca = peca[i];
            this.pos[linha][col].setOcupada(true);
            this.pos[linha][col].setSimbolo(this.pos[linha][col].peca.desenho());
        }
        for(linha=8, col=1, i=24; col<=8 && i<32; col++, i++){
            this.pos[linha][col].peca = peca[i];
            this.pos[linha][col].setOcupada(true);
            this.pos[linha][col].setSimbolo(this.pos[linha][col].peca.desenho());
        }

    }

 

}