import controller.ClienteController;
import controller.EnderecoController;
import model.ClienteModel;
import model.EnderecoModel;

import javax.xml.transform.Source;
import java.util.List;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ClienteController clienteController = new ClienteController();
        EnderecoController enderecoController = new EnderecoController();

        int op = -1;

        while (op!=0){
            System.out.println("---- MENU ----");
            System.out.println("0 - Sair");
            System.out.println("1 - Clientes");
            System.out.println("2 - Conta Bancaria");
            op = sc.nextInt();
            switch (op){
                case 1:
                    System.out.println("Selecione uma opção: ");
                    System.out.println("1 - Cadastro de Cliente");
                    System.out.println("2 - Buscar por CPF");
                    System.out.println("3 - Listar clientes");
                    System.out.println("4 - Deletar Cliente");
                    int opcliente = sc.nextInt();
                    switch (opcliente){
                        case 1:
                            sc.nextLine();
                            System.out.println("Digite seu CPF: ");
                            String cpf = sc.nextLine();

                            System.out.println("Digite seu nome completo: ");
                            String nome = sc.nextLine();

                            System.out.println("Digite seu telefone: ");
                            String telefone = sc.nextLine();

                            System.out.println("Digite o seu logradouro: ");
                            String logradouro =sc.nextLine();

                            System.out.println("Digite o seu numero: ");
                            int numero =sc.nextInt();
                            sc.nextLine();

                            System.out.println("Digite o seu CEP: ");
                            String cep =sc.nextLine();

                            System.out.println("Digite o seu município : ");
                            String municipio =sc.nextLine();

                            EnderecoModel enderecoCadastro = new EnderecoModel(logradouro,numero,cep,municipio);
                            ClienteModel cliente = new ClienteModel(cpf,nome,telefone,enderecoCadastro);
                            enderecoController.cadastrarEndereco(enderecoCadastro);
                            clienteController.cadastrarCliente(cliente);
                            break;
                        case 2:
                            System.out.println("Digite o CPF que deseja buscar: ");
                            sc.nextLine();
                            String cpfBusca = sc.nextLine();
                            clienteController.buscarPorCPF(cpfBusca);

                            break;
                        case 3:
                            List<ClienteModel> clientes = clienteController.listagemClientes();

                            System.out.println("\n---- CLIENTES CADASTRADOS ----");

                            for(ClienteModel c : clientes){
                                System.out.println("Id: " + c.getId());
                                System.out.println("Nome: " + c.getNome());
                                System.out.println("Cpf: " + c.getCpf());
                                System.out.println("Telefone: " + c.getTelefone());

                                //endereço
                                System.out.println("Logradouro: " + c.getEndereco().getLogradouro());
                                System.out.println("Número: " + c.getEndereco().getNumero());
                                System.out.println("Municipio: " + c.getEndereco().getMunicipio());
                                System.out.println("CEP: " + c.getEndereco().getCep());

                                System.out.println("--------------------------");
                            }
                            break;
                        case 4:
                            sc.nextLine();
                            System.out.println("Digite o CPF do cliente que deseja remover: ");
                            String cpfRemover = sc.nextLine();
                            clienteController.deletarCliente(cpfRemover);
                            break;
                        default:
                            break;
                    }
                    break;
                case 2:
                    System.out.println("Selecione uma opção");
                    break;
                default:
                    System.out.println(">> Opção inválida! Tente novamente.");
                    break;
            }
        }
        System.out.println("Encerrando o programa!");
    }
}