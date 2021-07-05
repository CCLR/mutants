package com.meli.exam.mutant.services;

import com.meli.exam.mutant.iservices.IStrandConfigurationBuilder;
import com.meli.exam.mutant.utils.Constants;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class StrandConfigurationBuilder implements IStrandConfigurationBuilder {

    /**
     * 1. Assigns the array size of the vertical strands configuration to the variable length <br>
     * 2. The number of diagonal lines of a square matrix can be found by multiplying the
     *    length of its columns or rows by 2 then subtracting 1.<br>
     * 3. The midPoint of a square matrix can be found by dividing the diagonalLines between 2 then add 1.<br>
     * 4. Create a list of StringBuilder to store the horizontal and oblique strands configuration found in the vertical strands configuration.<br>
     *    - This list is initialized with a empty StringBuilder for each of the items in the verticalStrandsConfiguration array.<br>
     *    - Each of these StringBuilders will correspond to a horizontal strand configuration belonging to the DNA matrix.<br>
     * 5. ItemsInDiagonal corresponds to the number of items to be assigned to each oblique strand configuration.<br>
     * 6. A loop is created to iterate over the diagonal lines, since this number is larger than length.<br>
     * 7. RowIndex is used to get a specific row in the verticalStrandsConfiguration.<br>
     * 8. In a square Matrix, you have two diagonals, the primary, and secondary.<br>
     *    - columnIndexPrimaryOblique and columnIndexSecondaryOblique are the variables with a defined index,
     *      to get a specific character of a String selected of verticalStrandsConfiguration array.<br>
     * 9. strandConfigurationPrimaryOblique and strandConfigurationSecondaryOblique are the variables for
     *    storing the configuration of the primary and secondary oblique strands.<br>
     * 10. If i is less than or equal to the midpoint, the elements located at and above the primary or secondary diagonal will be assigned,
     *     else the elements located below the primary or secondary diagonal will be assigned.<br>
     * 11. Only diagonals with more than 4 items will be added to the verticalObliqueStrandsConfiguration array.<br>
     *
     * @param verticalStrandsConfiguration
     * @return array of StringBuilders, consisting of the vertical and oblique strands configuration
     */
    @Override
    public List<StringBuilder> getVerticalObliqueStrandConfiguration(List<? extends CharSequence> verticalStrandsConfiguration) {
        int length = verticalStrandsConfiguration.size();
        int diagonalLines = (length * 2) - 1;
        var midPoint = (diagonalLines / 2) + 1;
        List<StringBuilder> verticalObliqueStrandsConfiguration = verticalStrandsConfiguration.stream().map(s -> new StringBuilder()).collect(Collectors.toList());
        var itemsInDiagonal = 0;

        for (var i = 1; i <= diagonalLines; i++) {
            int rowIndex;
            int columnIndexPrimaryOblique;
            int columnIndexSecondaryOblique;
            var strandConfigurationPrimaryOblique = new StringBuilder();
            var strandConfigurationSecondaryOblique = new StringBuilder();

            if (i <= midPoint) {
                itemsInDiagonal++;
                for (var j = 0; j < itemsInDiagonal; j++) {
                    rowIndex = (i - j) - 1;
                    columnIndexPrimaryOblique = j;
                    columnIndexSecondaryOblique = length - 1 - j;
                    strandConfigurationPrimaryOblique.append(verticalStrandsConfiguration.get(rowIndex).charAt(columnIndexPrimaryOblique));
                    strandConfigurationSecondaryOblique.append(verticalStrandsConfiguration.get(rowIndex).charAt(columnIndexSecondaryOblique));
                    verticalObliqueStrandsConfiguration.get(j).append(verticalStrandsConfiguration.get(rowIndex).charAt(columnIndexPrimaryOblique));
                }
            } else {
                itemsInDiagonal--;
                for (var j = 0; j < itemsInDiagonal; j++) {
                    rowIndex = (length - 1) - j;
                    columnIndexPrimaryOblique = (i - length) + j;
                    columnIndexSecondaryOblique = ((length - i) + length - 1) - j;
                    strandConfigurationPrimaryOblique.append(verticalStrandsConfiguration.get(rowIndex).charAt(columnIndexPrimaryOblique));
                    strandConfigurationSecondaryOblique.append(verticalStrandsConfiguration.get(rowIndex).charAt(columnIndexSecondaryOblique));
                    verticalObliqueStrandsConfiguration.get(columnIndexPrimaryOblique).append(verticalStrandsConfiguration.get(rowIndex).charAt(columnIndexPrimaryOblique));
                }
            }

            if (itemsInDiagonal >= Constants.NUMBER_OF_IDENTICAL_LETTERS) {
                verticalObliqueStrandsConfiguration.add(strandConfigurationPrimaryOblique);
                verticalObliqueStrandsConfiguration.add(strandConfigurationSecondaryOblique);
            }
        }
        return verticalObliqueStrandsConfiguration;
    }
}
