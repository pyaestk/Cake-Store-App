import com.example.shoppingapp.domain.model.CheckoutItem
import com.example.shoppingapp.domain.model.PaymentItemModel
import com.example.shoppingapp.domain.model.PaymentMethod
import com.example.shoppingapp.domain.model.ShippingOption

data class PaymentUiState(
    val isLoading: Boolean = false,
    val error: String? = null,

    val shippingAddress: String = "",

    val checkoutItems: List<CheckoutItem> = emptyList(),

    val items: List<PaymentItemModel> = emptyList(),

    val selectedShipping: ShippingOption = ShippingOption.Standard,
    val selectedPaymentMethod: PaymentMethod = PaymentMethod.Card,

    val itemsTotal: Double = 0.0,
    val shippingFee: Double = 0.0,
    val grandTotal: Double = 0.0,

    val lastOrderId: String? = null
)
