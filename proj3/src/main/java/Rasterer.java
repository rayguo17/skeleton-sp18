import java.util.HashMap;
import java.util.Map;

/**
 * This class provides all code necessary to take a query box and produce
 * a query result. The getMapRaster method must return a Map containing all
 * seven of the required fields, otherwise the front end code will probably
 * not draw the output correctly.
 */
public class Rasterer {
    private int feetPerLongtitude = 288200;
    private static final double ROOT_ULLAT = 37.892195547244356, ROOT_ULLON = -122.2998046875,
            ROOT_LRLAT = 37.82280243352756, ROOT_LRLON = -122.2119140625;
    private Map<Integer,Double> LDPPMap;
    private static final int pixelSize = 256;
    public Rasterer() {
        // YOUR CODE HERE
        LDPPMap = new HashMap<>();
        double longDiff = ROOT_LRLON-ROOT_ULLON;
        double feetTotal = longDiff * feetPerLongtitude;
        for(int i=0;i<=7;i++){
            //calculate LDPP of each level
            int edgeCnt = (int)Math.pow(2.0,(double)i);
            int pixelTotal = edgeCnt * pixelSize;
            double lDPP = feetTotal/pixelTotal;
            LDPPMap.put(i,lDPP);
        }

    }

    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     *
     *     The grid of images must obey the following properties, where image in the
     *     grid is referred to as a "tile".
     *     <ul>
     *         <li>The tiles collected must cover the most longitudinal distance per pixel
     *         (LonDPP) possible, while still covering less than or equal to the amount of
     *         longitudinal distance per pixel in the query box for the user viewport size. </li>
     *         <li>Contains all tiles that intersect the query bounding box that fulfill the
     *         above condition.</li>
     *         <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     *     </ul>
     *
     * @param params Map of the HTTP GET request's query parameters - the query box and
     *               the user viewport width and height.
     *
     * @return A map of results for the front end as specified: <br>
     * "render_grid"   : String[][], the files to display. <br>
     * "raster_ul_lon" : Number, the bounding upper left longitude of the rastered image. <br>
     * "raster_ul_lat" : Number, the bounding upper left latitude of the rastered image. <br>
     * "raster_lr_lon" : Number, the bounding lower right longitude of the rastered image. <br>
     * "raster_lr_lat" : Number, the bounding lower right latitude of the rastered image. <br>
     * "depth"         : Number, the depth of the nodes of the rastered image <br>
     * "query_success" : Boolean, whether the query was able to successfully complete; don't
     *                    forget to set this to true on success! <br>
     */
    public Map<String, Object> getMapRaster(Map<String, Double> params) {
        //System.out.println(params);
        int depth = getDepth(params);
//        Map<String, Object> results = new HashMap<>();
//
//        results.put("depth",depth);
        Map <String,Object> partailDetail = getTiles(depth,params);
        //partailDetail.put("query_success",true);
//        System.out.println("Since you haven't implemented getMapRaster, nothing is displayed in "
//                           + "your browser.");
        return partailDetail;
    }
    //
    private Map<String,Object> getTiles(int depth, Map<String,Double> params){
        Map<String,Object> res = new HashMap<>();
        int edgeCnt = (int) Math.pow(2.0,(double)depth);
        double initialUlLong = ROOT_ULLON;
        double initialUlLat = ROOT_ULLAT;
        double targetUlLong = params.get("ullon");
        double targetUlLat = params.get("ullat");
        double horStep = (ROOT_LRLON-ROOT_ULLON)/edgeCnt;
        double verStep = (ROOT_ULLAT-ROOT_LRLAT)/edgeCnt;
        int ulHorTile = 0;
        for(ulHorTile=0;ulHorTile<edgeCnt;ulHorTile++){
            //start with upper left to right
            double endLong = initialUlLong+ulHorTile*horStep;
            if(endLong>targetUlLong){
                if(ulHorTile==0){
                    break;
                }
                ulHorTile=ulHorTile-1;
                break;
            }
        }
        int ulVerTile = 0;
        for(ulVerTile=0;ulVerTile<edgeCnt;ulVerTile++){
            //up to bottom
            double endLat = initialUlLat- ulVerTile*verStep;
            if(endLat<targetUlLat){
                if(ulVerTile==0){
                    break;
                }
                ulVerTile = ulVerTile-1;
                break;
            }
        }
        //System.out.println(ulHorTile);
        //System.out.println(ulVerTile);
        double initialLrlon = ROOT_LRLON;
        double initialLrlat = ROOT_LRLAT;
        double targetLrlon = params.get("lrlon");
        double targetLrlat = params.get("lrlat");

        int lrHorTile = 0;
        for(lrHorTile=0;lrHorTile<edgeCnt;lrHorTile++){
            double curLrlon = initialLrlon - lrHorTile * horStep;
            if(curLrlon<targetLrlon){
                if(lrHorTile==0){
                    break;
                }
                lrHorTile = lrHorTile-1;
                break;
            }
        }
        int lrVerTile =0;
        for(lrVerTile=0;lrVerTile<edgeCnt;lrVerTile++){
            double curLrlat = initialLrlat+lrVerTile*verStep;
            if(curLrlat>targetLrlat){
                if(lrVerTile==0){
                    break;
                }
                lrVerTile = lrVerTile-1;
                break;
            }
        }
        lrVerTile = edgeCnt - lrVerTile-1;
        lrHorTile = edgeCnt - lrHorTile - 1;
        if(lrHorTile==-1 || lrVerTile == -1 || ulHorTile==edgeCnt || ulVerTile==edgeCnt){
            res.put("query_success",false);
            return res;
        }
        res.put("query_success",true);
        //System.out.println(lrHorTile);
        //System.out.println(lrVerTile);
        res.put("left",ulHorTile);
        res.put("right",lrHorTile);
        res.put("up",ulVerTile);
        res.put("down",lrVerTile);
        double raster_ul_lon = initialUlLong + ulHorTile * horStep;
        double raster_lr_lon = initialUlLong + (lrHorTile+1) * horStep;
        double raster_ul_lat = initialUlLat - ulVerTile * verStep;
        double raster_lr_lat = initialUlLat - (lrVerTile + 1) * verStep;
        res.put("raster_ul_lon",raster_ul_lon);
        res.put("raster_lr_lon",raster_lr_lon);
        res.put("raster_ul_lat",raster_ul_lat);
        res.put("raster_lr_lat",raster_lr_lat);
        res.put("depth",depth);
        String[][] render_grid = new String[lrVerTile-ulVerTile+1][lrHorTile-ulHorTile+1];
        int gridY=0;
        int gridX =0;
        for(int y = ulVerTile;y<=lrVerTile;y++){

            for(int x = ulHorTile; x<=lrHorTile;x++){
                render_grid[gridY][gridX] = String.format("d%d_x%d_y%d.png",depth,x,y);
                gridX++;
            }
            gridX=0;
            gridY++;
        }
        res.put("render_grid",render_grid);

        return res;
    }
    private int getDepth(Map<String, Double> params){
        double lrlon = params.get("lrlon");
        double ullon = params.get("ullon");
        double longDiff = lrlon-ullon;
        double feetDiff = longDiff * feetPerLongtitude;
        double pixelWidth = params.get("w");
        double targetLdpp = feetDiff/pixelWidth;

        for(int i=0;i<=7;i++){
            double curLdpp = LDPPMap.get(i);
            if(curLdpp<=targetLdpp){
                return i;
            }
        }
        return 7;
    }

}
