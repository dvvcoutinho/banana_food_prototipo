ALTER TABLE produtos ALTER COLUMN preco TYPE DOUBLE PRECISION;
ALTER TABLE pedidos ALTER COLUMN valor_total TYPE DOUBLE PRECISION;
ALTER TABLE itens_pedido ALTER COLUMN preco_unitario TYPE DOUBLE PRECISION;

ALTER TABLE produtos ADD COLUMN emoji VARCHAR(10) DEFAULT '🍌';
ALTER TABLE produtos ADD COLUMN calda VARCHAR(100);
ALTER TABLE produtos ADD COLUMN destaque BOOLEAN NOT NULL DEFAULT FALSE;
ALTER TABLE produtos ADD COLUMN status VARCHAR(30);

ALTER TABLE produtos ADD COLUMN usuario_id BIGINT;
ALTER TABLE produtos ADD CONSTRAINT fk_produto_usuario
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE SET NULL;

CREATE TABLE tags (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE produto_tags (
    id_produto BIGINT NOT NULL,
    id_tag BIGINT NOT NULL,
    PRIMARY KEY (id_produto, id_tag),
    CONSTRAINT fk_produto_tags_produto FOREIGN KEY (id_produto) REFERENCES produtos(id) ON DELETE CASCADE,
    CONSTRAINT fk_produto_tags_tag FOREIGN KEY (id_tag) REFERENCES tags(id) ON DELETE CASCADE
);

INSERT INTO tags (nome) VALUES
('Sem Açúcar'),
('Vegano'),
('Mais Vendido'),
('Gelado');

INSERT INTO usuarios (nome, email, senha) VALUES
('Davi Cliente', 'davi@exemplo.com', '123456');

INSERT INTO produtos (nome, categoria, preco, emoji, descricao, calda, destaque, status, usuario_id) VALUES
('Banoffee Suprema com Calda de Toffee Dourado', 'doces', 16.50, '🍌', 'Camadas crocantes de biscoito, banana fresca selecionada, doce de leite artesanal e calda toffee escorrendo.', 'Toffee Dourado', TRUE, 'DISPONIVEL', (SELECT id FROM usuarios WHERE email = 'davi@exemplo.com')),
('Bolo de Banana Caramelizada com Calda Quente', 'bolos', 26.90, '🎂', 'Massa fofinha com centro quente e erupção de calda cremosa e bananas flambadas.', 'Caramelo Quente', TRUE, 'DISPONIVEL', (SELECT id FROM usuarios WHERE email = 'davi@exemplo.com')),
('Panquecas Americanas de Banana com Mel Dourado', 'sobremesas', 19.50, '🥞', 'Torre de panquecas fofas com fatias de banana e cascata de mel silvestre puro com brilho.', 'Mel Nobre', FALSE, 'DISPONIVEL', (SELECT id FROM usuarios WHERE email = 'davi@exemplo.com')),
('Banana Split Suprema com Calda Duo Chocolate & Caramelo', 'sobremesas', 24.90, '🍨', 'Sorvetes cremosos com banana fatiada e cascata de chocolate nobre e caramelo salgado.', 'Duo Chocolate & Caramelo', TRUE, 'DISPONIVEL', (SELECT id FROM usuarios WHERE email = 'davi@exemplo.com')),
('Waffle Belga com Banana Flambada e Canela', 'sobremesas', 22.00, '🧇', 'Waffle dourado e crocante coberto por bananas douradas na manteiga e calda de açúcar mascavo.', 'Caramelo de Canela', FALSE, 'DISPONIVEL', (SELECT id FROM usuarios WHERE email = 'davi@exemplo.com')),
('Pudim Tradicional com Calda Espelhada de Caramelo', 'doces', 14.00, '🍮', 'Pudim aveludado sem furinhos regado por uma farta calda de açúcar caramelizado brilhante.', 'Caramelo Espelhado', FALSE, 'DISPONIVEL', (SELECT id FROM usuarios WHERE email = 'davi@exemplo.com')),
('Smoothie de Banana & Morango com Borda de Calda', 'bebidas', 15.00, '🥤', 'Bebida ultra cremosa e refrescante servida em copo decorado com calda de frutas vermelhas.', 'Frutas Vermelhas', FALSE, 'DISPONIVEL', (SELECT id FROM usuarios WHERE email = 'davi@exemplo.com')),
('Cappuccino Banana Toffee Cremoso', 'bebidas', 13.90, '☕', 'Café expresso especial com leite vaporizado, aroma suave de banana e fios de calda toffee.', 'Toffee Especial', FALSE, 'DISPONIVEL', (SELECT id FROM usuarios WHERE email = 'davi@exemplo.com'));

INSERT INTO produto_tags (id_produto, id_tag)
SELECT produtos.id, tags.id FROM produtos, tags
WHERE produtos.nome LIKE 'Banoffee%' AND tags.nome = 'Mais Vendido';

INSERT INTO produto_tags (id_produto, id_tag)
SELECT produtos.id, tags.id FROM produtos, tags
WHERE (produtos.nome LIKE 'Banana Split%' OR produtos.nome LIKE 'Smoothie%') AND tags.nome = 'Gelado';
