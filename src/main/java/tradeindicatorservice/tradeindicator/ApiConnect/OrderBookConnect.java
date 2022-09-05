package tradeindicatorservice.tradeindicator.ApiConnect;


import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import tradeindicatorservice.tradeindicator.Indicator.OrderBookResult;

import java.util.ArrayList;
import java.util.List;

public class OrderBookConnect {

    public List<OrderBookResult> getOrderBook(String market){
        try{
            OkHttpClient client = new OkHttpClient();
            Gson gson = new Gson();

            Request request = new Request.Builder()
                    .url("https://api.upbit.com/v1/orderbook?markets="+market)
                    .get()
                    .addHeader("Accept", "application/json")
                    .build();

            Response response = client.newCall(request).execute();

            if(response.isSuccessful()){
                String result = response.body().string();
                JSONParser jsonParser = new JSONParser();
                JSONArray results = (JSONArray) jsonParser.parse(result.toString());
                JSONObject temp = (JSONObject) results.get(0);

                JSONArray tempList = (JSONArray) jsonParser.parse(temp.get("orderbook_units").toString());

                List<OrderBookResult> list = new ArrayList<>();

                for(int i = 0; i < tempList.size(); i++){
                    JSONObject object = (JSONObject) tempList.get(i);

                    OrderBookResult orderBookResult = gson.fromJson(object.toJSONString(), OrderBookResult.class);
                    list.add(orderBookResult);
                }

                return list;
            }

            return null;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
