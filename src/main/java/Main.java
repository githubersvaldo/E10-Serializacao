import java.util.Date;

public class Main {
    public static void main(String[] args) {
        // Criando um cliente que é uma pessoa física
        Cliente pessoa = new PessoaFisica("Davi","Meu Deus",new Date(),"111111111-11",26,'m');

        // Criando uma conta corrente para esse cliente
        Conta cc = new ContaCorrente(pessoa);

        //Fazendo operações de saques e depósitos
        //cc.setLimite(1500);
        try {
            cc.depositar(1000);
            cc.depositar(2000);
            cc.sacar(500);
            cc.depositar(3000);
            cc.sacar(10);
            cc.sacar(15);
        }catch (ValorNegativoException e){
            System.out.println(e.getMessage());
        }catch (SemLimiteExeption f){
            System.out.println(f.getMessage());
        }
        try{
            cc.salvarConta();
        }catch(NaoFoiPossivelSalvar e){
            System.out.println(e.getMessage());
        }
        cc.imprimirExtrato();
        System.out.println(cc);

    }
}