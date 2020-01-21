function change(){
    var tmp = document.getElementById("inputState").value;
    document.getElementById("inputState").value = document.getElementById("outputState").value;
    document.getElementById("outputState").value = tmp;
}

