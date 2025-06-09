import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.recommender.*;

import java.io.File;
import java.util.List;

public class RecommenderSystem {

    public static void main(String[] args) {
        try {
            // Load user-item-rating data from CSV
            File dataFile = new File("dataset.csv");
            DataModel model = new FileDataModel(dataFile);

            // Calculate user similarity using Pearson correlation
            UserSimilarity similarity = new PearsonCorrelationSimilarity(model);

            // Find nearest 2 neighbors
            UserNeighborhood neighborhood = new NearestNUserNeighborhood(2, similarity, model);

            // Create recommender
            Recommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);

            // Recommend top 3 items for user ID 1
            List<RecommendedItem> recommendations = recommender.recommend(1, 3);

            // Output the recommendations
            for (RecommendedItem item : recommendations) {
                System.out.println("Recommended Item: " + item.getItemID() +
                                   " | Estimated Preference: " + item.getValue());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
