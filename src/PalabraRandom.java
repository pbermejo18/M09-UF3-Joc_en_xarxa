public class PalabraRandom {
    /* Classe que genera numeros aleatoris per jugar a adivinar-los i els comprova
     * tant si rep un numero o una string.
     */

    private String paraula;

    public PalabraRandom() {
        paraula = "";
        pensa();
    }

    public String pensa() {
        // setNum((int) ((Math.random()*max)+1));
        String[] paraules = {"Menja", "Pizza", "Pinya", "Linux"};

        int numero = (int) (Math.random()*4);
        paraula = paraules[numero];
        //System.out.println("He pensat la paraula " + getParaula());

        return paraula;
    }

    public String comprova(String n) {
        if(paraula.equals(n)) return "0";
        else return "1";
    }
/*
    public String comprova(String s) {
        int n = Integer.parseInt(s);
        if(paraula.equals(s)) return "Correcte";
        else return "Una altre paraula";
    }
*/
    public String getParaula() {
        return paraula;
    }

    public void setParaula(String paraula) {
        this.paraula = paraula;
    }

    @Override
    public String toString() {
        return "SecretNum{" +
                "paraula='" + paraula + '\'' +
                '}';
    }
}