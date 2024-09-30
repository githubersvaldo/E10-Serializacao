import java.util.Date;

public class Main {
    public static void main(String[] args) {
        // Criando um cliente que é uma pessoa física
        Cliente pessoa = new PessoaFisica("Davi","Meu Deus",new Date(),"111111111-11",26,'m');

        // Criando uma conta corrente para esse cliente
        Conta cc = new ContaCorrente(pessoa);
        Conta cc2 = null;
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
        //cc.imprimirExtrato();
        try{
            cc2 = cc.Ver_C_Salva(1001,741996316);
        }catch (Exception e){
            System.out.println(1);
            System.out.println(e.getMessage());
        }
        System.out.println(cc);
        System.out.println(cc2);

    }
}