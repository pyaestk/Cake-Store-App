import com.example.shoppingapp.domain.model.PaymentMethod
import com.example.shoppingapp.domain.model.ShippingOption

data class PaymentItemResponse(
    val id: String,
    val title: String,
    val subtitle: String,
    val imageUrl: String?,
    val qty: Int,
    val price: Double
)

data class PaymentSummaryResponse(
    val items: List<PaymentItemResponse>,
    val selectedShipping: ShippingOption,
    val selectedPaymentMethod: PaymentMethod,
    val itemsTotal: Double,
    val shippingFee: Double,
    val grandTotal: Double
)

data class PayResultResponse(
    val orderId: String,
    val status: String
)