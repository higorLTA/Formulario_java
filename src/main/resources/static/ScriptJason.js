"use strict";
const limparCampos = (endereco) =>{
    document.getElementById('endereco').value = ""
    document.getElementById('bairro').value = ""
    document.getElementById('cidade').value = ""
    document.getElementById('estado').value = ""

}

const campos = (endereco) =>{
    document.getElementById('endereco').value = endereco.logradouro
    document.getElementById('bairro').value = endereco.bairro
    document.getElementById('cidade').value = endereco.localidade
    document.getElementById('estado').value = endereco.uf

}
const eNumero = (numero) => /^[0-9]+$/.test(numero)

const cepValido = (cep) => cep.length == 8 && eNumero(cep);

const buscarCep = async() => {
    limparCampos()
    const cep =  document.querySelector('#cep').value
    const url = `http://viacep.com.br/ws/${cep}/json/`;
    if(cepValido(cep)){
    const dados = await fetch(url);
    const endereco = await dados.json()
    if(endereco.hasOwnProperty('erro')){
        document.getElementById('endereco').value = 'Cep inexistente!!!'
    }else{
        campos(endereco)  
    }

    }else{
        document.getElementById('endereco').value = 'Formato de cep Incorreto!!!'
}

    
}

document.querySelector('#cep').addEventListener("focusout", buscarCep);