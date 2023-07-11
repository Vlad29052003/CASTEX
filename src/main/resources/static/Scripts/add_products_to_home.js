// Step 1: Fetch product data from the server
function fetchProducts() {
    return fetch('/api/product') // Replace with your API endpoint for fetching products
        .then(response => response.json())
        .catch(error => console.error('Error fetching products:', error));
}

// Step 2: Generate HTML markup for each product
function generateProductListings(products) {
    return products.filter(product => product.photo !== null).map(product => {

        //console.log("PULAMEA" + product.photo + "PULAMEA")
        //product.photo = pako.inflateRaw(product.photo)

        var image = new Image();
        image.src = 'data:image/png;base64,'+product.photo;
        //document.body.appendChild(image);
        product.photo=image;
        console.log(product)
        return `<div th:replace="~{fragments/catalog_item :: item(product=${product})}"></div>`;

    }).join('');
}

// Step 3: Select the target element in the HTML
const bMidElement = document.querySelector('.b_mid');

// Step 4: Insert the generated HTML into the selected element
window.onload = function () {
    //console.log(fetchProducts())
    fetchProducts()
        .then(products => {
            const productHTML = generateProductListings(products);
            bMidElement.innerHTML = productHTML;
        })
        .catch(error => console.error('Error inserting product listings:', error));
};