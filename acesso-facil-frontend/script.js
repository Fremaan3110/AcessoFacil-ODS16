
const formulario = document.getElementById("formSolicitacao");
const relato = document.getElementById("relato");
const contador = document.getElementById("contadorCaracteres");

const botaoEnviar = document.getElementById("botaoEnviar");
const mensagemErro = document.getElementById("mensagemErro");

const telaFormulario = document.getElementById("telaFormulario");
const telaResultado = document.getElementById("telaResultado");

const novaSolicitacao = document.getElementById("novaSolicitacao");

// Contador de caracteres
relato.addEventListener("input", () => {
    contador.textContent = relato.value.length;
});

// Enviar solicitação
formulario.addEventListener("submit", async (event) => {

    event.preventDefault();

    const texto = relato.value.trim();

    if (!texto) {
        mensagemErro.textContent = "Digite sua solicitação.";
        return;
    }

    botaoEnviar.disabled = true;
    botaoEnviar.textContent = "Enviando...";
    mensagemErro.textContent = "";

    try {
        const resposta = await fetch(
            "http://localhost:8080/api/solicitacoes",
            {
                method: "POST",

                headers: {
                    "Content-Type": "application/json"
                },

                body: JSON.stringify({
                    relato: texto
                })
            }
        );

        if (!resposta.ok) {
            throw new Error("Erro ao enviar solicitação.");
        }

        const dados = await resposta.json();

        const analise = dados.analiseIA;

        document.getElementById("resultadoCategoria")
            .textContent = analise?.categoria ?? "Não informada";

        document.getElementById("resultadoUrgencia")
            .textContent = analise?.nivelUrgencia ?? "Não informada";

        document.getElementById("resultadoOrientacao")
            .textContent = analise?.orientacao ?? "Orientação indisponível.";

        telaFormulario.classList.add("escondido");
        telaResultado.classList.remove("escondido");

        telaResultado.scrollIntoView({
            behavior: "smooth"
        });

    } catch (erro) {
        console.error(erro);

        mensagemErro.textContent =
            "Não foi possível enviar. Verifique se o servidor está funcionando.";
    } finally {
        botaoEnviar.disabled = false;
        botaoEnviar.textContent = "Enviar solicitação";
    }
});

// Fazer outra solicitação
novaSolicitacao.addEventListener("click", () => {

    formulario.reset();
    contador.textContent = "0";
    mensagemErro.textContent = "";

    telaResultado.classList.add("escondido");
    telaFormulario.classList.remove("escondido");

    telaFormulario.scrollIntoView({
        behavior: "smooth"
    });
});
