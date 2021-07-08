//Felipe Leite Kogus
//RA: 771006

public class Dama extends Peca{

    Dama(){
        this.setEmJogo(true);
    }

    //construtor altera para true o booleano emJogo e inicializa a cor da peca
    Dama(char cor){
        this.setEmJogo(true);
        this.setCor(cor);
    }

    public char getCor() {
        return this.cor;
    }

    public void setCor(char cor) {
        this.cor = cor;
    }

    @Override
    public char desenho(){
        if(this.getCor() == 'B'){
            int temp = 9813;
            return( (char)temp );
        }
        else if(this.getCor() == 'P'){
            int x = 9819;
            return ((char)x);
        }
        else return 'x';  //nunca acontecera, esta aqui apenas para tirar a mensagem de erro
    }

    /**@param linha1 linha inicial
     * @param col1 coluna inicial
     * @param linha2 linha final
     * @param col2 coluna final
     * @return boolean true se for possivel fazer o movimento e false caso contrario
     */
    @Override
    public boolean checaMovimento(int linha1, char col1, int linha2, char col2){
//as letras sao passadas para maiuscula, para que sejam aceitas tanto letras maiusculas como minusculas
        col1 = java.lang.Character.toUpperCase(col1);
        col2 = java.lang.Character.toUpperCase(col2);

        if(!this.status()){
            System.out.println("Movimento inválido!");
            return false;
        }

        int coluna1 = (int) col1-64;
        int coluna2 = (int) col2-64;

        if(linha1 == linha2 && coluna1 == coluna2) return false;
        else if(linha1 == linha2){
            return true;
        }
        else if(coluna1 == coluna2){
            return true;
        }
        else if(Math.abs(linha2 - linha1) == Math.abs(coluna2 - coluna1)){
            return true;
        }
        else {
            return false;
        }

    }



}
