window.onload = () => {
    const messageDiv = document.getElementById("message-div");
    if (messageDiv) {
        setTimeout(() => {
            messageDiv.remove();
        }, 3000);
    }
}