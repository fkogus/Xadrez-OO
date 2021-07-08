//Felipe Leite Kogus
//RA: 771006

public class Peao extends Peca{

    Peao(){
        this.setEmJogo(true);
    }

    //construtor altera para true o booleano emJogo e inicializa a cor da peca
    Peao(char cor){
        this.setEmJogo(true);
        this.setCor(cor);
    }

    @Override
    public char desenho(){ //retorna o simbolo relacionado com a peca
        if(this.getCor() == 'B'){
            int temp = 9817;
            return( (char)temp );
        }
        else if(this.getCor() == 'P'){
            int x = 9823;
            return ((char)x);
        }
        else return 'x'; //nunca acontecera, esta aqui apenas para sumir com a mensagem de erro do editor
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

        if(!this.status()) {
            System.out.println("Movimento inválido!");
            return false;
        }

        int coluna1 = (int)(col1-64);
        //System.out.printf("coluna1 = %d\n", coluna1);
        int coluna2 = (int)(col2-64);
        //System.out.printf("coluna2 = %d\n", coluna2);

        if(linha1 == linha2 && coluna1 == coluna2) return false;


        else if(this.getCor() == 'B'){

            if(linha1 == 2){
                if((coluna2 - coluna1) == 0 && (linha2 - linha1 == 1 || linha2 - linha1 == 2)  
                && linha2 > linha1){
                    //System.out.printf("aqui 1\n");
                    return true;
                }
                 else {
                    return false;
                }
            }
            else{
                if((coluna2 - coluna1) == 0 && linha2 - linha1 == 1 && linha2 > linha1){
                    //System.out.printf("aqui 2\n");
                    return true;
                }
                else return false;
            }
        }


        else if(this.getCor() == 'P'){
            if(linha1 == 7){
                if((coluna2 - coluna1) == 0 && (linha2 - linha1 == -1 || linha2 - linha1 == -2) 
                && linha2 < linha1){
                    //System.out.printf("linha1 = %d\nlinha2 = %d\n", linha1, linha2);
                    //System.out.printf("aqui 3\n");
                    return true;
                }
                else {
                    return false;
                }
            }
            else{
                if((coluna2 - coluna1) == 0 && linha2 - linha1 == -1 && linha2 < linha1){
                    //System.out.printf("aqui 4\n");
                    return true;
                }
                else return false;
            }

        }

        else return false;

    }

   

}
