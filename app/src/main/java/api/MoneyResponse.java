package api;
import com.example.loftmoney.items.Item;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class MoneyResponse {
    @SerializedName("status")
        private String status;
        @SerializedName("data")
        private List<Item> moneyItemsList;
        public String getStatus() {
            return status;
        }
        public List<Item> getMoneyItemsList() {
            return moneyItemsList;
        }
    }
