import sun.nio.cs.Surrogate;

import java.io.*;
import java.util.Random;

public abstract class Conta implements ITaxas, Serializable {

    private int numero;

    protected Cliente dono;

    protected double saldo;

    protected int agencia;

    private double limite;

    private Operacao[] operacoes = new Operacao[6];

    private int proximaOperacao = 0;

    private static int totalContas = 0;

    public Conta() {
        Random gerador = new Random();
        this.agencia = 1001;
        int vai = gerador.nextInt();
        while(vai < 0){
            vai = gerador.nextInt();
        }
        this.numero = vai;
    }
    public void sacar(double valor)
        throws ValorNegativoException,SemLimiteExeption{
        if(valor < limite+saldo) {
            if (valor > 0) {
                this.saldo -= valor;

                this.operacoes[proximaOperacao] = new OperacaoSaque(valor);
                this.proximaOperacao++;
            } else {
                throw new ValorNegativoException("Digite um valor possitivo");
            }
        }else{
            throw new SemLimiteExeption("Sem limite tente um valor menor");
        }
    }

    public void depositar(double valor) throws ValorNegativoException{
        if(valor <= 0) {
            throw new ValorNegativoException("Digite um valor possitivo");
        }
        this.saldo += valor;
        this.operacoes[proximaOperacao] = new OperacaoDeposito(valor);
        this.proximaOperacao++;
    }

    public void transferir(Conta destino, double valor)
            throws ValorNegativoException,SemLimiteExeption{
        if (valor >= 0 && valor <= this.limite) {
            this.sacar(valor);
            try {
                destino.depositar(valor);
            }catch (ValorNegativoException e) {
                throw new ValorNegativoException("Digite um valor possitivo");
            }
        }else{
            throw new SemLimiteExeption("Sem limite tente um valor menor");
        }
    }
    @Override
    public String toString() {
        return this.numero + "\n" + this.dono.toString() + this.saldo + "\n" + this.limite;
    }
    @Override
    public boolean equals(Object obj) {
        return this.numero == ((Conta) obj).numero;
    }

    public void imprimirExtrato() {
        System.out.println("======= Extrato " + dono.getNome() + " ======");
        for(Operacao atual : this.operacoes) {
            if (atual != null) {
                System.out.println(atual.toString());
            }
        }
        System.out.println("=======================");
    }
    public double calculaTaxas(){return 0;}
    public void imprimirExtratoTaxas(){
        double Total = calculaTaxas();
        System.out.printf("======= Extrato %s Taxas ======",dono.getNome());
        System.out.printf("Manutenção da conta: %.2f\n" ,this.calculaTaxas());
        System.out.printf("Operações\n");
        for(int i = 0; i < proximaOperacao; i++) {
            if(operacoes[i].calculaTaxas() != 0) {
                System.out.printf("%s %.2f\n", operacoes[i].getTipo(), operacoes[i].calculaTaxas());
                Total += operacoes[i].calculaTaxas();
            }
        }
        this.saldo -= Total;
        System.out.printf("Total: %.2f\n", Total);
    }
    public void salvarConta() throws NaoFoiPossivelSalvar{
        try{
            FileOutputStream SaiDaQui = new FileOutputStream(agencia + "-" + numero + ".ser");
            ObjectOutputStream out = new ObjectOutputStream(SaiDaQui);
            out.writeObject(this);
            out.close();
            SaiDaQui.close();
        }catch (Exception e){
            e.printStackTrace();
            throw new NaoFoiPossivelSalvar("Ocoreu um erro ao salvar o arquivo");
        }
    }
    public Conta Ver_C_Salva(int agenciaT, int numeroConta) throws IOException, ClassNotFoundException {
        FileInputStream VemAqui = new FileInputStream(agenciaT + "-" + numeroConta + ".ser");
        ObjectInputStream EsseMemo = new ObjectInputStream(VemAqui);
        Conta VeriCont = (Conta) EsseMemo.readObject();
        EsseMemo.close();
        VemAqui.close();
        return VeriCont;
    }

    public int getNumero() {
        return numero;
    }

    public Cliente getDono() {
        return dono;
    }

    public double getSaldo() {
        return saldo;
    }

    public double getLimite() {
        return limite;
    }

    public static int getTotalContas() {
        return Conta.totalContas;
    }


    public void setDono(Cliente dono) {
        this.dono = dono;
    }

    public abstract double setLimite(double limite);
}
