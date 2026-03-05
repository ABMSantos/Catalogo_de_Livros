package br.com.alura.literalura.principal;

import br.com.alura.literalura.model.*;
import br.com.alura.literalura.repository.AutorRepository;
import br.com.alura.literalura.repository.LivroRepository;
import br.com.alura.literalura.service.ConsumoApi;
import br.com.alura.literalura.service.ConverteDados;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {
    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumoApi = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private final String ENDERECO = "https://gutendex.com/books/?search=";

    private LivroRepository livroRepository;
    private AutorRepository autorRepository;

    public Principal(LivroRepository livroRepository, AutorRepository autorRepository) {
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
    }

    public void exibeMenu() {
        var opcao = -1;
        while (opcao != 0) {
            var menu = """
                    \n*** LiterAlura ***
                    1 - Buscar livro pelo título
                    2 - Listar livros registrados
                    3 - Listar nossos autores
                    4 - Listar autores em determinado ano
                    5 - Listar livros em determinado idioma
                    0 - Sair
                    
                    Escolha uma opção:""";

            System.out.println(menu);
            try {
                opcao = Integer.parseInt(leitura.nextLine());
                switch (opcao) {
                    case 1 -> buscarLivroWeb();
                    case 2 -> listarLivrosRegistrados();
                    case 3 -> listarAutoresRegistrados();
                    case 4 -> listarAutoresVivos();
                    case 5 -> listarLivrosPorIdioma();
                    case 0 -> System.out.println("Saindo do LiterAlura...");
                    default -> System.out.println("Opção inválida!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor, digite um número válido.");
            }
        }
    }

    private void buscarLivroWeb() {
        System.out.println("Digite o nome do livro que deseja buscar:");
        var nomeLivro = leitura.nextLine();
        var json = consumoApi.obterDados(ENDERECO + nomeLivro.replace(" ", "%20"));
        DadosResposta dados = conversor.obterDados(json, DadosResposta.class);

        if (dados.resultados() != null && !dados.resultados().isEmpty()) {
            DadosLivro dadosLivro = dados.resultados().get(0);
            
            if (livroRepository.existsByTitulo(dadosLivro.titulo())) {
                System.out.println("Este livro já está cadastrado no banco de dados!");
                return;
            }

            DadosAutor dadosAutor = dadosLivro.autores().get(0);
            Autor autor = autorRepository.findByNome(dadosAutor.nome())
                    .orElseGet(() -> autorRepository.save(new Autor(dadosAutor)));

            Livro livro = new Livro(dadosLivro, autor);
            livroRepository.save(livro);
            System.out.println("Livro salvo com sucesso: \n" + livro);
        } else {
            System.out.println("Livro não encontrado na API.");
        }
    }

    private void listarLivrosRegistrados() {
        List<Livro> livros = livroRepository.findAll();
        if (livros.isEmpty()) {
            System.out.println("Nenhum livro cadastrado.");
        } else {
            livros.forEach(System.out::println);
        }
    }

    private void listarAutoresRegistrados() {
        List<Autor> autores = autorRepository.findAll();
        if (autores.isEmpty()) {
            System.out.println("Nenhum autor cadastrado.");
        } else {
            autores.forEach(System.out::println);
        }
    }

    private void listarAutoresVivos() {
        System.out.println("Digite o ano que deseja pesquisar:");
        try {
            var ano = Integer.parseInt(leitura.nextLine());
            List<Autor> autoresVivos = autorRepository.autoresVivosNoAno(ano);
            if (autoresVivos.isEmpty()) {
                System.out.println("Nenhum autor vivo encontrado neste ano em nosso banco.");
            } else {
                autoresVivos.forEach(System.out::println);
            }
        } catch (NumberFormatException e) {
            System.out.println("Ano inválido. Digite apenas números.");
        }
    }

    private void listarLivrosPorIdioma() {
        System.out.println("Insira o idioma para busca (ex: pt, en, fr, es):");
        var idioma = leitura.nextLine().toLowerCase();
        List<Livro> livros = livroRepository.findByIdioma(idioma);
        if (livros.isEmpty()) {
            System.out.println("Nenhum livro encontrado nesse idioma no banco de dados.");
        } else {
            livros.forEach(System.out::println);
        }
    }
}