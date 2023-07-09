let name = document.getElementById("p_name");
let price = document.getElementById("p_price");
let stock = document.getElementById("p_stock");
let category = document.getElementById("p_category");
let image = document.getElementById("p_img");
let short_description = document.getElementById("p_short_description");
let long_description = document.getElementById("p_long_description");
let button = document.getElementById("p_button");

button.addEventListener("click",checkAddProduct);

async function checkAddProduct(event) {
    event.preventDefault();

    const compressedData = pako.deflate(image.value); // Compress image data using pako

    const byteArray = Array.from(compressedData); // Convert the Uint8Array to a regular JavaScript array

    const json = JSON.stringify(byteArray); // Convert the array to a JSON string

    console.log(json);

    const params = {
        id: 0,
        name: name.value,
        photo: byteArray,
        longDescription: long_description.value,
        shortDescription: short_description.value,
        category: category.value,
        price: price.value,
        rating: 0,
        stock: stock.value
    };
    console.log(JSON.stringify(params))
    addProduct(params);

}

function addProduct(params) {
    fetch(server + "/api/product/add-a-product", {
        method: 'POST', headers: {
            'Content-Type': 'application/json'
        }, body: JSON.stringify(params)
    })
        .then(response => {
            let promise = response.text();
            if (response.ok) {
                alert("Product added successfully");
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert(error);
        });
}