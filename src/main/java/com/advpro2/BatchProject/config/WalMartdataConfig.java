    package com.advpro2.BatchProject.config;

    import com.advpro2.BatchProject.entity.WalMartdata;
    import org.springframework.batch.item.ItemProcessor;

    //This is our processor where we write down the whole logic
    public class WalMartdataConfig  implements ItemProcessor<WalMartdata,WalMartdata>   {

        @Override
        public WalMartdata process(WalMartdata item) throws  Exception{
              if(item.getFuelPrice()>2.7){
                item.setPricrinr(item.getFuelPrice()*83.47);
                if(item.getPricrinr()>270){
                    item.setTemp_celcius((item.getTemperature()-32)*5/9);
                }
                else{
                    item.setTemp_celcius(item.getTemperature());
                }

              }
              else{
                  item.setPricrinr(item.getFuelPrice());
                  item.setTemp_celcius(item.getTemperature());
              }
            return item;
        }
    }
