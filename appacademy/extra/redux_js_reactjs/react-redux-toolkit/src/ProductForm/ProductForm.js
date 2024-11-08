import { useDispatch, useSelector } from "react-redux"
import { addProduct } from "../store/reducers/Product";
import { useState } from "react";

export function ProductForm() {
    const products = useSelector(r => r.product);
    const dispatch = useDispatch();

    const [product, setProduct] = useState("");
    const [price, setPrice] = useState(0);

    return (
        <div className="container">
            <h1>Add a Product</h1>
            <form id="productForm">
                <div className="form-group">
                    <label htmlFor="productName">Product Name:</label>
                    <input type="text" id="productName" name="productName" required onChange={(e) => setProduct(e.target.value)} />
                </div>
                <div className="form-group">
                    <label htmlFor="productPrice">Product Price:</label>
                    <input type="number" id="productPrice" name="productPrice" step="0.01" required onChange={(e) => setPrice(e.target.value)} />
                </div>
                <button type="button" className="btn" id="addBtn" onClick={
                    () => dispatch(
                        addProduct(
                            {
                                name: product,
                                price: price
                            }
                        )
                    )
                }>Add</button>
                <button type="button" className="btn" id="checkoutBtn">Checkout</button>
            </form>

            <div className="product-list" id="productList">
                <h2>Product List</h2>
                {
                    products.map(({ name, price }, k) => <div key={k} className="product-item">{`Name: ${name}, Price: $${parseFloat(price).toFixed(2)}`}</div>)
                }
            </div>
        </div>
    )
}