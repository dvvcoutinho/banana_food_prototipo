# 🍌 Banana Food - Sobremesas & Caldas Nobres

## 👥 Integrantes da Dupla
- **Davi** (Matrícula: 2024200925)
- **[Nome do Segundo Integrante]** (Matrícula: [Número de Matrícula])

---

## 📝 Descrição do Projeto
O **Banana Food** é um sistema web desenvolvido originalmente como protótipo em HTML, CSS e JavaScript e agora evoluído para uma aplicação web robusta utilizando **Java**, **Spring Boot** e o motor de templates **Thymeleaf**.

O sistema representa uma confeitaria artesanal especializada em sobremesas nobres à base de banana e caldas gourmet fervidas no ponto de calda (toffee dourado, caramelo salgado, mel silvestre, calda de frutas vermelhas e chocolate nobre).

Nesta versão com Spring Boot:
- As páginas HTML estáticas foram transformadas em templates dinâmicos processados pelo **Thymeleaf**.
- A navegação é 100% gerenciada por rotas definidas em **Controllers Spring MVC**.
- Dados simulados em memória (**Service**) alimentam dinamicamente a vitrine do cardápio, a página inicial e a tela de detalhes de cada produto.
- Um formulário completo com método **POST** permite cadastrar novas sobremesas artesanais em memória e redireciona automaticamente para a listagem atualizada (padrão POST-Redirect-GET).

---

## 🚀 Tecnologias Utilizadas
- **Java 26 / OpenJDK (compatível com Java 17+)**
- **Spring Boot 4.x / 3.x**
  - `spring-boot-starter-webmvc` (Spring Web MVC para roteamento e Controllers)
  - `spring-boot-starter-thymeleaf` (Template Engine para renderização dinâmica)
  - `spring-boot-devtools` (Hot reload e produtividade de desenvolvimento)
- **Thymeleaf** (`th:text`, `th:each`, `th:href`, `th:action`, `th:object`, `th:field`, `th:if`, etc.)
- **HTML5 Semântico** & **CSS3 Moderno** (Variáveis, gradientes, layout responsivo, efeitos de calda líquida)
- **JavaScript Moderno** (Gerenciamento de carrinho em `localStorage`, toasts interativos e modais)
- **Apache Maven Wrapper (`mvnw`)**

---

## 📁 Estrutura de Diretórios do Projeto
```text
demo/
├── pom.xml
├── mvnw
├── mvnw.cmd
├── README.md
└── src/
    └── main/
        ├── java/
        │   └── com/
        │       └── bananafood/
        │           └── demo/
        │               ├── DemoApplication.java
        │               ├── controller/
        │               │   ├── HomeController.java        # Rota raiz /
        │               │   ├── ProdutoController.java     # /menu, /produtos/{id}, /produtos/novo
        │               │   ├── CarrinhoController.java    # /carrinho
        │               │   └── UsuarioController.java     # /login-cadastro, /cadastrar-usuario
        │               ├── model/
        │               │   ├── Produto.java               # Entidade de sobremesas e caldas
        │               │   └── Usuario.java               # Entidade de clientes e autenticação
        │               └── service/
        │                   ├── ProdutoService.java        # Dados simulados em memória (Thread-Safe)
        │                   └── UsuarioService.java        # Gestão de clientes em memória
        └── resources/
            ├── application.properties
            ├── static/                                    # Arquivos estáticos servidos pelo Spring Boot
            │   ├── css/
            │   │   ├── style.css
            │   │   ├── index.css
            │   │   ├── menu.css
            │   │   ├── carrinho.css
            │   │   ├── login-cadastro.css
            │   │   ├── detalhes.css
            │   │   └── cadastro.css
            │   ├── js/
            │   │   └── script.js
            │   └── img/
            └── templates/                                 # Telas dinâmicas Thymeleaf
                ├── index.html                             # Página inicial / Dashboard de destaques
                ├── menu.html                              # Listagem dinâmica com th:each e filtros
                ├── detalhes.html                          # Visualização detalhada de um produto
                ├── cadastro.html                          # Formulário integrado ao backend via POST
                ├── carrinho.html                          # Resumo do pedido e cupom
                └── login-cadastro.html                    # Acesso e cadastro de cliente
```

---

## 🌐 Principais Rotas Disponíveis

| Método HTTP | Rota | Descrição | Integração Thymeleaf / Controller |
| :--- | :--- | :--- | :--- |
| **GET** | `/` | Página inicial com destaques do cardápio | `HomeController` envia `destaques` via `Model` para `th:each` |
| **GET** | `/menu` | Cardápio completo com filtro de categoria e busca | `ProdutoController` envia `produtos` para `th:each` e contadores |
| **GET** | `/produtos/{id}` | Ficha técnica e detalhes da sobremesa | `ProdutoController` busca por ID e envia `produto` para `th:text` |
| **GET** | `/produtos/novo` | Formulário para adicionar nova sobremesa | `ProdutoController` envia `new Produto()` associado a `th:object` |
| **POST** | `/produtos` | Salva a sobremesa na lista em memória | Recebe dados do formulário e redireciona para `/menu` |
| **GET** | `/carrinho` | Tela de pedido e resumo do carrinho | `CarrinhoController` renderiza a página do pedido |
| **GET** | `/login-cadastro` | Central de login e cadastro de clientes | `UsuarioController` envia formulário de autenticação |
| **POST** | `/cadastrar-usuario` | Salva novo cliente simulado em memória | Recebe dados do usuário via POST e confirma cadastro |

---

## ⚙️ Como Executar o Projeto

### Pré-requisitos
- **Java JDK 17** ou superior (o projeto foi compilado e testado com OpenJDK 26 e é compatível com Java 17/21/26).
- Não é necessário ter o Maven instalado globalmente, pois o projeto inclui o **Maven Wrapper** (`mvnw` / `mvnw.cmd`).

### Passo a Passo

1. **Clonar o Repositório:**
   ```bash
   git clone https://github.com/feijaomxx/banana_food_prototipo.git
   cd banana_food_prototipo
   ```

2. **Compilar e Baixar Dependências:**
   - No Windows (PowerShell/CMD):
     ```powershell
     .\mvnw compile
     ```
   - No Linux/macOS:
     ```bash
     ./mvnw compile
     ```

3. **Iniciar a Aplicação Spring Boot:**
   - No Windows (PowerShell/CMD):
     ```powershell
     .\mvnw spring-boot:run
     ```
   - No Linux/macOS:
     ```bash
     ./mvnw spring-boot:run
     ```

4. **Acessar no Navegador:**
   Abra seu navegador favorito e acesse:
   ```text
   http://localhost:8080
   ```

5. **Testando as Funcionalidades:**
   - Navegue pelo menu superior em **"Cardápio de Caldas"** (`http://localhost:8080/menu`).
   - Clique em **"🔍 Ver"** em qualquer sobremesa para visualizar a tela de detalhes (`http://localhost:8080/produtos/1`).
   - Clique em **"➕ Nova Sobremesa"** (`http://localhost:8080/produtos/novo`), preencha os dados e clique em **"Salvar Sobremesa no Cardápio"**.
   - Observe o redirecionamento para o cardápio com o novo item exibido instantaneamente através da lista em memória no backend!

---

## 🧪 Demonstração dos Critérios de Avaliação

- ✅ **Estrutura Spring Boot Oficial**: Separação clara entre `controller`, `model`, `service`, `static` e `templates`.
- ✅ **Thymeleaf Dinâmico**: Uso extensivo de `th:text`, `th:each`, `th:href`, `th:action`, `th:object`, `th:field`, `th:if`, `th:classappend`.
- ✅ **Formulário com POST e Redirecionamento**: Formulário de criação de produtos (`GET /produtos/novo` ➔ `POST /produtos` ➔ `redirect:/menu`).
- ✅ **Dados Simulados em Memória**: Lista de produtos inicializada com 8 itens detalhados gerenciados por `ProdutoService`.
- ✅ **Navegação Funcional**: Todas as rotas mapeadas no Spring MVC sem links para arquivos `.html`.
- ✅ **Reaproveitamento do Protótipo Visual**: Toda a identidade visual (efeito de calda escorrendo, tipografia, paleta doce) mantida e enriquecida.
