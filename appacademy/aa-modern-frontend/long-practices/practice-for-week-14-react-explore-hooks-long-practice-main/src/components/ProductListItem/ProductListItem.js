import React from 'react'

function ProductListItem({ product, isSelected, onClick }) {
  return (
    <div className={`product-list-item ${isSelected ? ' selected' : ''}`}>
      <img className="product-list-item-photo"
           src={product.photo.filename}
           alt={`${product.name}`}
      />
      <button onClick={(product)=> onClick(product)}>{product.name}</button>
    </div>
  )
}

export default ProductListItem;
