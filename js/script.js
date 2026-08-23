const PRODUTOS_CATALOGO = [
    {
        id: 1,
        nome: "Banoffee Suprema com Calda de Toffee Dourado",
        categoria: "doces",
        preco: 16.50,
        emoji: "🍌",
        descricao: "Camadas crocantes de biscoito, banana fresca selecionada, doce de leite artesanal e calda toffee escorrendo.",
        calda: "Toffee Dourado"
    },
    {
        id: 2,
        nome: "Bolo de Banana Caramelizada com Calda Quente",
        categoria: "bolos",
        preco: 26.90,
        emoji: "🎂",
        descricao: "Massa fofinha com especiarias e cobertura vulcânica de caramelo fervente e bananas flambadas.",
        calda: "Caramelo Quente"
    },
    {
        id: 3,
        nome: "Panquecas Americanas de Banana com Mel Dourado",
        categoria: "sobremesas",
        preco: 19.50,
        emoji: "🥞",
        descricao: "Torre de panquecas fofas com fatias de banana e cascata de mel silvestre puro com brilho.",
        calda: "Mel Nobre"
    },
    {
        id: 4,
        nome: "Banana Split Suprema com Calda Duo Chocolate & Caramelo",
        categoria: "sobremesas",
        preco: 24.90,
        emoji: "🍨",
        descricao: "Banana in natura com três bolas de sorvete, chantilly, cereja e caldas escorrendo pelas bordas.",
        calda: "Duo Chocolate & Caramelo"
    },
    {
        id: 5,
        nome: "Waffle Belga com Banana Flambada e Canela",
        categoria: "sobremesas",
        preco: 22.00,
        emoji: "🧇",
        descricao: "Waffle dourado e crocante coberto por bananas douradas na manteiga e calda de açúcar mascavo.",
        calda: "Caramelo de Canela"
    },
    {
        id: 6,
        nome: "Pudim Tradicional com Calda Espelhada de Caramelo",
        categoria: "doces",
        preco: 14.00,
        emoji: "🍮",
        descricao: "Pudim aveludado sem furinhos regado por uma farta calda de açúcar caramelizado brilhante.",
        calda: "Caramelo Espelhado"
    },
    {
        id: 7,
        nome: "Smoothie de Banana & Morango com Borda de Calda",
        categoria: "bebidas",
        preco: 15.00,
        emoji: "🥤",
        descricao: "Bebida ultra cremosa e refrescante servida em copo decorado com calda de frutas vermelhas.",
        calda: "Frutas Vermelhas"
    },
    {
        id: 8,
        nome: "Cappuccino Banana Toffee Cremoso",
        categoria: "bebidas",
        preco: 13.90,
        emoji: "☕",
        descricao: "Café expresso especial com leite vaporizado, aroma suave de banana e fios de calda toffee.",
        calda: "Toffee Especial"
    }
];

function obterCarrinho() {
    const carrinhoSalvo = localStorage.getItem('banana_food_carrinho') || localStorage.getItem('calda_gourmet_carrinho');
    return carrinhoSalvo ? JSON.parse(carrinhoSalvo) : [];
}

function salvarCarrinho(carrinho) {
    localStorage.setItem('banana_food_carrinho', JSON.stringify(carrinho));
    atualizarContadorHeader();
}

function adicionarAoCarrinho(id, nome, preco, emoji) {
    const carrinho = obterCarrinho();
    const itemExistente = carrinho.find(item => item.id === id);

    if (itemExistente) {
        itemExistente.quantidade += 1;
    } else {
        carrinho.push({
            id: id,
            nome: nome,
            preco: Number(preco),
            emoji: emoji,
            quantidade: 1
        });
    }

    salvarCarrinho(carrinho);
    mostrarToast(`"${nome}" adicionado ao pedido!`, 'sucesso', '🍌');
}

function alterarQuantidadeItem(id, delta) {
    let carrinho = obterCarrinho();
    const item = carrinho.find(item => item.id === id);

    if (item) {
        item.quantidade += delta;
        if (item.quantidade <= 0) {
            carrinho = carrinho.filter(i => i.id !== id);
            mostrarToast(`Item removido do carrinho.`, 'alerta', '🗑️');
        }
        salvarCarrinho(carrinho);
        renderizarPaginaCarrinho();
    }
}

function removerItemCarrinho(id) {
    let carrinho = obterCarrinho();
    carrinho = carrinho.filter(item => item.id !== id);
    salvarCarrinho(carrinho);
    mostrarToast(`Item removido do carrinho.`, 'alerta', '🗑️');
    renderizarPaginaCarrinho();
}

function limparCarrinho() {
    localStorage.removeItem('banana_food_carrinho');
    localStorage.removeItem('calda_gourmet_carrinho');
    atualizarContadorHeader();
    renderizarPaginaCarrinho();
}

function atualizarContadorHeader() {
    const carrinho = obterCarrinho();
    const totalItens = carrinho.reduce((total, item) => total + item.quantidade, 0);
    const elementosContador = document.querySelectorAll('.contador-carrinho');

    elementosContador.forEach(el => {
        el.textContent = totalItens;
    });
}

let descontoAtivo = 0;

function renderizarPaginaCarrinho() {
    const containerItens = document.getElementById('carrinho-lista-container');
    const resumoSubtotalEl = document.getElementById('resumo-subtotal');
    const resumoDescontoEl = document.getElementById('resumo-desconto');
    const resumoTotalEl = document.getElementById('resumo-total');
    const carrinhoVazioEl = document.getElementById('carrinho-vazio-aviso');
    const asideResumoEl = document.getElementById('resumo-pedido-aside');

    if (!containerItens) return;

    const carrinho = obterCarrinho();

    if (carrinho.length === 0) {
        if (containerItens) containerItens.innerHTML = '';
        if (carrinhoVazioEl) carrinhoVazioEl.style.display = 'flex';
        if (asideResumoEl) asideResumoEl.style.display = 'none';
        return;
    }

    if (carrinhoVazioEl) carrinhoVazioEl.style.display = 'none';
    if (asideResumoEl) asideResumoEl.style.display = 'block';

    let subtotal = 0;
    let htmlItens = '';

    carrinho.forEach(item => {
        const itemTotal = item.preco * item.quantidade;
        subtotal += itemTotal;

        htmlItens += `
      <article class="item-pedido-card">
        <div class="item-visual" aria-hidden="true">${item.emoji}</div>
        <div class="item-detalhes">
          <h3>${item.nome}</h3>
          <span class="item-preco-unitario">R$ ${item.preco.toFixed(2).replace('.', ',')} un.</span>
        </div>
        <div class="item-quantidade-controle" aria-label="Controle de Quantidade">
          <button type="button" class="btn-qtd" onclick="alterarQuantidadeItem(${item.id}, -1)" aria-label="Diminuir">-</button>
          <span class="qtd-valor">${item.quantidade}</span>
          <button type="button" class="btn-qtd" onclick="alterarQuantidadeItem(${item.id}, 1)" aria-label="Aumentar">+</button>
        </div>
        <div class="item-subtotal-box">
          <span class="item-preco-total">R$ ${itemTotal.toFixed(2).replace('.', ',')}</span>
          <button type="button" class="btn-remover-item" onclick="removerItemCarrinho(${item.id})">
            Remover ✕
          </button>
        </div>
      </article>
    `;
    });

    containerItens.innerHTML = htmlItens;

    const valorDesconto = subtotal * descontoAtivo;
    const taxaEntrega = 5.00;
    const totalFinal = Math.max(0, (subtotal - valorDesconto + taxaEntrega));

    if (resumoSubtotalEl) resumoSubtotalEl.textContent = `R$ ${subtotal.toFixed(2).replace('.', ',')}`;
    if (resumoDescontoEl) resumoDescontoEl.textContent = `- R$ ${valorDesconto.toFixed(2).replace('.', ',')}`;
    if (resumoTotalEl) resumoTotalEl.textContent = `R$ ${totalFinal.toFixed(2).replace('.', ',')}`;
}

function aplicarCupom() {
    const inputCupom = document.getElementById('input-cupom');
    if (!inputCupom) return;

    const codigo = inputCupom.value.trim().toUpperCase();

    if (codigo === 'BANANAFOOD' || codigo === 'DOCE15' || codigo === 'BANANA10') {
        descontoAtivo = 0.15;
        mostrarToast('Cupom Especial de 15% aplicado com sucesso!', 'sucesso', '🎟️');
        renderizarPaginaCarrinho();
    } else if (codigo === '') {
        mostrarToast('Por favor, digite um código de cupom.', 'alerta', '⚠️');
    } else {
        mostrarToast('Cupom inválido. Experimente "BANANAFOOD" ou "DOCE15"!', 'alerta', '❌');
    }
}

function finalizarPedido() {
    const carrinho = obterCarrinho();
    if (carrinho.length === 0) {
        mostrarToast('Seu carrinho está vazio!', 'alerta', '⚠️');
        return;
    }

    const modal = document.getElementById('modal-pedido-confirmado');
    if (modal) {
        modal.showModal();
        limparCarrinho();
    } else {
        alert('🎉 Pedido Confirmado com Sucesso!');
        limparCarrinho();
    }
}

function fecharModalPedido() {
    const modal = document.getElementById('modal-pedido-confirmado');
    if (modal) {
        modal.close();
        window.location.href = 'menu.html';
    }
}

function renderizarVitrineProdutos(filtroCategoria = 'todos', termoBusca = '') {
    const container = document.getElementById('vitrine-produtos-grid');
    if (!container) return;

    let produtosFiltrados = PRODUTOS_CATALOGO;

    if (filtroCategoria !== 'todos') {
        produtosFiltrados = produtosFiltrados.filter(p => p.categoria === filtroCategoria);
    }

    if (termoBusca) {
        const termo = termoBusca.toLowerCase();
        produtosFiltrados = produtosFiltrados.filter(p =>
            p.nome.toLowerCase().includes(termo) ||
            p.descricao.toLowerCase().includes(termo) ||
            p.calda.toLowerCase().includes(termo)
        );
    }

    if (produtosFiltrados.length === 0) {
        container.innerHTML = `
      <div class="menu-vazio">
        <span>🔍</span>
        <h3>Nenhum item encontrado</h3>
        <p>Tente buscar por outro termo ou selecione a categoria "Todos".</p>
      </div>
    `;
        return;
    }

    let html = '';
    produtosFiltrados.forEach(prod => {
        html += `
      <article class="produto-card">
        <header class="produto-drip-header">
          <svg viewBox="0 0 500 40" preserveAspectRatio="none">
            <path d="M0,0 C100,35 150,5 250,30 C350,50 420,10 500,25 L500,0 Z"></path>
          </svg>
          <span class="badge-calda-tipo">Calda: ${prod.calda}</span>
        </header>

        <figure class="produto-figura">
          <span class="produto-emoji" role="img" aria-label="${prod.nome}">${prod.emoji}</span>
          <figcaption class="sr-only">${prod.nome}</figcaption>
        </figure>

        <div class="produto-info">
          <div class="produto-titulos">
            <h3>${prod.nome}</h3>
            <p>${prod.descricao}</p>
          </div>

          <footer class="produto-rodape">
            <div class="preco-valor">
              <small>R$</small> ${prod.preco.toFixed(2).replace('.', ',')}
            </div>
            <button type="button" class="btn btn-primary btn-sm" onclick="adicionarAoCarrinho(${prod.id}, '${prod.nome}', ${prod.preco}, '${prod.emoji}')">
              Adicionar +
            </button>
          </footer>
        </div>
      </article>
    `;
    });

    container.innerHTML = html;
}

function alternarAbasAuth(abaDesejada) {
    const btnLogin = document.getElementById('tab-btn-login');
    const btnCadastro = document.getElementById('tab-btn-cadastro');
    const formLogin = document.getElementById('form-login');
    const formCadastro = document.getElementById('form-cadastro');

    if (!btnLogin || !btnCadastro || !formLogin || !formCadastro) return;

    if (abaDesejada === 'login') {
        btnLogin.classList.add('ativo');
        btnCadastro.classList.remove('ativo');
        formLogin.classList.add('ativo');
        formCadastro.classList.remove('ativo');
    } else {
        btnCadastro.classList.add('ativo');
        btnLogin.classList.remove('ativo');
        formCadastro.classList.add('ativo');
        formLogin.classList.remove('ativo');
    }
}

function processarLogin(event) {
    event.preventDefault();
    const email = document.getElementById('login-email').value;
    mostrarToast(`Bem-vindo(a) ao Banana Food! Login realizado para: ${email}`, 'sucesso', '🍌');
}

function processarCadastro(event) {
    event.preventDefault();
    const nome = document.getElementById('cad-nome').value;
    mostrarToast(`Conta criada para ${nome} com sucesso!`, 'sucesso', '🎉');
    setTimeout(() => {
        alternarAbasAuth('login');
    }, 1500);
}

function mostrarToast(mensagem, tipo = 'sucesso', icone = '🍌') {
    let container = document.getElementById('toast-container');
    if (!container) {
        container = document.createElement('div');
        container.id = 'toast-container';
        document.body.appendChild(container);
    }

    const toast = document.createElement('div');
    toast.className = `toast ${tipo}`;
    toast.innerHTML = `
    <span class="toast-icone">${icone}</span>
    <span class="toast-msg">${mensagem}</span>
  `;

    container.appendChild(toast);

    setTimeout(() => {
        toast.style.opacity = '0';
        toast.style.transform = 'translateX(100%)';
        toast.style.transition = 'all 0.3s ease';
        setTimeout(() => {
            if (toast.parentNode) toast.parentNode.removeChild(toast);
        }, 300);
    }, 3500);
}

document.addEventListener('DOMContentLoaded', () => {
    atualizarContadorHeader();

    const btnMobile = document.getElementById('botao-menu-mobile');
    const navMenu = document.getElementById('menu-principal-nav');
    if (btnMobile && navMenu) {
        btnMobile.addEventListener('click', () => {
            navMenu.classList.toggle('aberto');
        });
    }

    const containerVitrine = document.getElementById('vitrine-produtos-grid');
    if (containerVitrine) {
        renderizarVitrineProdutos('todos');

        const botoesCategoria = document.querySelectorAll('.btn-categoria');
        botoesCategoria.forEach(btn => {
            btn.addEventListener('click', () => {
                botoesCategoria.forEach(b => b.classList.remove('ativo'));
                btn.classList.add('ativo');
                const cat = btn.getAttribute('data-categoria');
                const inputBusca = document.getElementById('input-busca-produtos');
                const termo = inputBusca ? inputBusca.value : '';
                renderizarVitrineProdutos(cat, termo);
            });
        });

        const inputBusca = document.getElementById('input-busca-produtos');
        if (inputBusca) {
            inputBusca.addEventListener('input', (e) => {
                const catAtiva = document.querySelector('.btn-categoria.ativo');
                const cat = catAtiva ? catAtiva.getAttribute('data-categoria') : 'todos';
                renderizarVitrineProdutos(cat, e.target.value);
            });
        }
    }

    if (document.getElementById('carrinho-lista-container')) {
        renderizarPaginaCarrinho();
    }

    const formLogin = document.getElementById('form-login');
    if (formLogin) {
        formLogin.addEventListener('submit', processarLogin);
    }

    const formCadastro = document.getElementById('form-cadastro');
    if (formCadastro) {
        formCadastro.addEventListener('submit', processarCadastro);
    }
});
