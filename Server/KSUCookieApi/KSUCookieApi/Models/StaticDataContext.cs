﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace KSUCookieApi.Models
{
    public enum Category { Cookie, Milk };

    public class StaticDataContext
    {
        private static int productIdToUse = 0;
        private static List<ProductsModel> _productList;
        public static List<ProductsModel> ProductList 
        { 
            get 
            {
                if (_productList == null)
                    _productList = InitializeProductList();
                return _productList; 
            }
        }

        private static List<OrderModel> _orderList;
        public static List<OrderModel> OrderList
        {
            get
            {
                if (_orderList == null)
                    _orderList = new List<OrderModel>();
                return _orderList;
            }
        }

        public static void ResetOrderList()
        {
            _orderList = null;
        }

        public static ProductsModel CreateProductEntry(decimal price, Category cat, string name, string desc)
        {
            return new ProductsModel
            {
                ProductId = productIdToUse++,
                Name = name,
                Price = price,
                ProductCategory = cat,
                Description = desc
            };
        }

        public static void AddNewProduct(ProductsModel Prod)
        {
            if (!ProductList.Exists(x => x.Name == Prod.Name))
            {
                ProductList.Add(Prod);
            }
        }

        public static List<ProductsModel> InitializeProductList()
        {
            var productList = new List<ProductsModel>();
            productList.Add(CreateProductEntry(1m, Category.Cookie, "Chocolate Chip", "Warm, gooey Chocolate Chips!"));
            productList.Add(CreateProductEntry(1.25m, Category.Cookie, "Peanut Butter", "Caution--May Contain Peanut Products"));
            productList.Add(CreateProductEntry(1.5m, Category.Cookie, "Sugar", "WHEEE!!"));
            productList.Add(CreateProductEntry(2.25m, Category.Cookie, "Oatmeal Raisin", "It's Oatmeal.  It's Raisin.  It's both!"));
            productList.Add(CreateProductEntry(.5m, Category.Milk, "Whole", "Pours like a milkshake"));
            productList.Add(CreateProductEntry(.75m, Category.Milk, "2%", "Just Like Mom used to buy"));
            productList.Add(CreateProductEntry(.55m, Category.Milk, "Skim", "To help you watch your waistline"));
            productList.Add(CreateProductEntry(1.3m, Category.Milk, "Soy", "Well... this is really soy juice but who cares?"));
            return productList;
        }

        public static void UpdateQuantity(OrderModel order)
        {
            int index = _orderList.FindIndex(o => o.ProductId == order.ProductId);

            _orderList[index].Quantity += order.Quantity;
        }

        public static void AddItemToOrder(OrderModel order)
        {
            _orderList.Add(new OrderModel { ProductId = order.ProductId, Quantity = order.Quantity });
        }

        public void Remove(int id)
        {
            _orderList.RemoveAll(o => o.ProductId == id);
        }
    }
}