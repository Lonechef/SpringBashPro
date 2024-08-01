    package com.advpro2.BatchProject.config;

    import com.advpro2.BatchProject.entity.WalMartdata;
    import org.springframework.batch.item.ItemProcessor;

    //This is our processor where we write down the whole logic
    public class WalMartdataConfig  implements ItemProcessor<WalMartdata,WalMartdata>   {

        @Override
        public WalMartdata process(WalMartdata item) throws  Exception{
              if(item.getFuelPrice()>2.5){
                item.setPricrinr(item.getFuelPrice()*83.47);
              }
              else{
                  item.setPricrinr(item.getPricrinr());
              }
            return item;
        }
    }
