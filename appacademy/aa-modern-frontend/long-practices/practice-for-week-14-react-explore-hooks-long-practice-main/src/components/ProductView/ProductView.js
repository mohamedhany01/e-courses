import React, { useEffect, useState } from 'react';
import ProductListItem from "../ProductListItem";
import ProductDetails from "../ProductDetails";
import './ProductView.css'

function ProductView({ products }) {

  const [sideOpen, setSideOpen] = useState(true);
  const [selectedProducts, setselectedProducts] = useState([]);
  const [highlitedProducts, setHighlitedProducts] = useState(new Set());

  useEffect(() => {

    if (!sideOpen) {
      setHighlitedProducts(new Set())
    } else {
      setHighlitedProducts(prevHighlightedProducts => {
        const newHighlightedProducts = new Set(prevHighlightedProducts);
        selectedProducts.forEach(p => newHighlightedProducts.add(p.id));
        return newHighlightedProducts;
      });
    }

  }, [sideOpen]);

  return (
    <div className="product-view">
      <div className="product-main-area">
        <h1>Products</h1>
        <div className="product-list">
          {products.map(item =>
            <ProductListItem
              key={item.id}
              product={item}
              isSelected={highlitedProducts.has(item.id)}
              onClick={() => {
                setselectedProducts([...selectedProducts, item]);
                setHighlitedProducts(new Set([...highlitedProducts, item.id]));
                setSideOpen(true);
              }}
            />
          )}
        </div>
      </div>
      <div className="product-side-panel">
        <div className="product-side-panel-toggle-wrapper">
          <div className="product-side-panel-toggle"
            onClick={() => setSideOpen(!sideOpen)}>
            {sideOpen ? '>' : '<'}
          </div>
        </div>
        <ProductDetails visible={sideOpen} products={selectedProducts} />
      </div>
    </div>
  );
}

export default ProductView;