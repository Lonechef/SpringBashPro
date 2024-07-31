    package com.advpro2.BatchProject.config;

    import com.advpro2.BatchProject.entity.WalMartdata;
    import org.springframework.batch.item.ItemProcessor;

    //This is our processor where we write down the whole logic
    public class WalMartdataConfig  implements ItemProcessor<WalMartdata,WalMartdata>   {

        @Override
        public WalMartdata process(WalMartdata item) throws  Exception{

    //        if(item.getFuelprice()>2.5){
    //            //item=item*83.47;
    //            return item;
    //
    //        }

            return item;
        }


    }
