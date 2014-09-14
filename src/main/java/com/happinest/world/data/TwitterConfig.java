package com.happinest.world.data;

import com.happinest.world.service.QuickTwitterApiTest;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.happinest.world.data.TwitterTokens.*;

public class TwitterConfig {

    public Configuration getTwitterConfiguration(int token){
        Map<TwitterTokens, String> tokens = getTwiterConfigs().get(token);
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(tokens.get(ConsumerKey))
                .setOAuthConsumerSecret(tokens.get(ConsumerSecret))
                .setOAuthAccessToken(tokens.get(AccessToken))
                .setOAuthAccessTokenSecret(tokens.get(AccessTokenSecret));
        return cb.build();
    }


    public List<Map<TwitterTokens, String>> getTwiterConfigs(){
        List<Map<TwitterTokens, String>> result = new ArrayList<>();
        Map<TwitterTokens, String> config1 = new HashMap<>();
        config1.put(ConsumerKey, "f4ZmrjJ2buZQ6S6UuMIT57dny");
        config1.put(ConsumerSecret, "oWNP3S8ovgmCytKd3o0isa8IHZPxC9EDPVlrNWbbVSHFhZgC9J");
        config1.put(AccessToken, "154181159-Vp1yTELVhOiedH15LDtlybm32vQlLCLwVmflsOkV");
        config1.put(AccessTokenSecret, "tMywu9TA79JhqXVVsS7eNwa4lIcdM460Tf8Wv2LvusgHm");
        Map<TwitterTokens, String> config2 = new HashMap<>();
        config2.put(ConsumerKey, "nLLCagUwtQdRJ7eLQPKq9ZF4L");
        config2.put(ConsumerSecret, "yL3QHkPabeIuk6vHJE3W0Rae5BLoRcamqHmpPJZKQyDP3YyOVc");
        config2.put(AccessToken, "154181159-oNjKhTMKcTIDeyUA5E9NtNGev94quKj90HN4TKlT");
        config2.put(AccessTokenSecret, "KFFmm7TiMn7QjeBe7zgR21je8vfnBFKbjS5TbtmtPKG43");
        Map<TwitterTokens, String> config3 = new HashMap<>();
        config3.put(ConsumerKey, "3ReRAfQBUue8E6dcS2syuT5g1");
        config3.put(ConsumerSecret, "tmOgQ3f0tYbLcymCxpZA5iV8ha1O7TEuXovXkYJ1HzoMftS0mD");
        config3.put(AccessToken, "2807915978-wrTRfjZHV9dLhcK5HGrayMeYbRFO677wDCm29bg");
        config3.put(AccessTokenSecret, "012vZ3LBdtJnqIk9XdvhUOxXvNwfgOT5tGm0Eum63OnFp");
        Map<TwitterTokens, String> config4 = new HashMap<>();
        config4.put(ConsumerKey, "p9cC4duIZINlUUglcSZubXVXv");
        config4.put(ConsumerSecret, "UFEKS3EGjClRD37BKirkG2qqGg7bpOt7C7dfZgX8iMJzIR8DrU");
        config4.put(AccessToken, "2808969062-yTbsqSSEyFKNvQKiELh6B5ujcBQdbwTIrKrOGK1");
        config4.put(AccessTokenSecret, "a3tSYQuJ7YMgNd5u5u5Y7mz9ArVfOPvItBZFhHveCkGRR");
        result.add(config1);
        result.add(config2);
        result.add(config3);
        result.add(config4);
        return  result;
    }

}
