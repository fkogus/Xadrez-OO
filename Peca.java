//Felipe Leite Kogus
//RA: 771006

public abstract class Peca {
    
    protected boolean emJogo;
    protected int linha;
    protected char coluna;
    protected char cor;

    /**
     * @return Char que representa a peca no tabuleiro
     */
   public abstract char desenho();

   /**@param linha1 linha inicial
     * @param col1 coluna inicial
     * @param linha2 linha final
     * @param col2 coluna final
     * @return boolean true se for possivel fazer o movimento e false caso contrario
     */
   public abstract boolean checaMovimento(int linha1, char col1, int linha2, char col2);

   public boolean status(){
    return this.emJogo;
    }

    public void setEmJogo(boolean emJogo) {
        this.emJogo = emJogo;
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

    public char getCor() {
        return this.cor;
    }

    public void setCor(char cor) {
        this.cor = cor;
    }
    

}
