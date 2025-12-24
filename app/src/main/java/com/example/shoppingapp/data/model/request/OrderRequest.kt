data class OrderRequest(
    val itemsTotal: Double,
    val shippingFee: Double,
    val grandTotal: Double,
    val shippingOption: String,
    val paymentMethod: String,
    val items: List<OrderItemRequest>
)

data class OrderItemRequest(
    val itemId: String,
    val title: String,
    val subtitle: String,
    val imageUrl: String?,
    val qty: Int,
    val price: Double
)
