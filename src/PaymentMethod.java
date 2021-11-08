
public enum PaymentMethod {
	CREDITCARD {
        public String toString() {
            return "Credit Card";
        }
	},
	PAYPAL {
        public String toString() {
            return "PayPal";
        }
	}
}
