public class Jogador {
    
    private String nome;
    private char cor; //das pecas
    protected Peca[] peca = new Peca[16];

    /**Atribui o nome e a cor das pecas do jogador e cria o vetor de pecas correspondente
     */
    Jogador(String nome, char cor){
        this.nome = nome;
        this.cor = cor;

        if(cor == 'B') this.criarPecasBrancas();
        else if(cor == 'P') this.criarPecasPretas();
    }

    /**As pecas serao armazenadas no vetor na seguinte ordem:
     * 0 a 7: peoes;
     * restante: na ordem em que serão colocados no tabuleiro 
     */

    private void criarPecasBrancas(){
     
        char x; //aux
        int i; //aux

        for(x = 'A', i=0; x <= 'H'; x++, i++){
            peca[i] = new Peao('B');
        }

        peca[8] = new Torre('B');
        peca[9] = new Cavalo('B');
        peca[10] = new Bispo('B');
        peca[11] = new Dama('B');
        peca[12] = new Rei('B');
        peca[13] = new Bispo('B');
        peca[14] = new Cavalo('B');
        peca[15] = new Torre('B');

    }

    /**As pecas serao armazenadas no vetor na seguinte ordem:
     * 0 a 7: peoes;
     * restante: na ordem em que serão colocados no tabuleiro 
     */
    private void criarPecasPretas(){

        char x; //aux
        int i; //aux

        for(x = 'A', i=0; x <= 'H'; x++, i++){
            peca[i] = new Peao('P');
        }

        peca[8] = new Torre('P');
        peca[9] = new Cavalo('P');
        peca[10] = new Bispo('P');
        peca[11] = new Dama('P');
        peca[12] = new Rei('P');
        peca[13] = new Bispo('P');
        peca[14] = new Cavalo('P');
        peca[15] = new Torre('P');

    }


    public String getNome() {
        return this.nome;
    }

    public char getCor() {
        return this.cor;
    }

}
