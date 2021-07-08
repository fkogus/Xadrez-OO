//Felipe Leite Kogus
//RA: 771006

public class Posicao {
    
    private char cor, simbolo;
    private int linha;
    private char coluna;
    private boolean ocupada;
    protected Peca peca;
    
    /**
     * Inicializa a posicao e escolhe seu simbolo dependendo de suas coordenadas
     * @param linha Linha da posicao
     * @param coluna Coluna da posicao
     */
    Posicao(int linha, int coluna){ //paridades diferentes = branca; iguais = preta
        this.setOcupada(false);
        this.setLinha(linha);
        char col = (char)(coluna+64);
        this.setColuna(col);
        if(linha % 2 == 0 && coluna % 2 != 0 && linha!=0 && coluna!=0){
            this.setCor('B');
            this.setSimbolo((char)9633); //quadrado branco
        }
        else if(linha % 2 != 0 && coluna % 2 == 0 && linha!=0 && coluna!=0){
            this.setCor('B');
            this.setSimbolo((char)9633); //quadrado branco
        }
        else if (linha!=0 && coluna!=0){
            this.setCor('P');
            this.setSimbolo((char)9632); //quadrado preto
        }
    
        else if(linha == 0 && coluna>0){
            this.setSimbolo((char)(coluna+64)); //Colunas de A a H
        }
        else if(coluna == 0 && linha>0){
            this.setSimbolo((char)(linha+48)); //Numeros de 1 a 8
        }
    }

    public char getSimbolo() {
        return this.simbolo;
    }

    public void setSimbolo(char simbolo) {
        this.simbolo = simbolo;
    }

    public void setCor(char cor) {
        this.cor = cor;
    }

    public int getLinha() {
        return this.linha;
    }

    public void setLinha(int linha) {
        this.linha = linha;
    }

    public char getColuna() {
        return this.coluna;
    }

    public void setColuna(char coluna) {
        this.coluna = coluna;
    }

    public boolean isOcupada() {
        return this.ocupada;
    }

    public void setOcupada(boolean ocupada) {
        this.ocupada = ocupada;
    }


    public Peca getPeca() {
        return this.peca;
    }

    public void setPeca(Peca peca) {
        this.peca = peca;
    }

}
