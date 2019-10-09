package com.KoreyMacGill.randomdungeongenerator.dungeonFlavorGenerator;

public class LocationGenerator {

    public String determineDungeonLocation(boolean allowExoticLocations)
    {
        /*
        So I made this section a switch as opposed to a if/elseif chain so that we could
        in theory add new entries into the subrange as well as trivially add new cases.
        I used the cascade rule for the switch such that if case 1 were chosen it would fall
        to case 4. -Koko
         */

        String location = "";
        int roll = (int) (Math.random() * 100) + 1;

        while(location.isEmpty())
        {
            switch(roll)
            {
                case 1:
                case 2:
                case 3:
                case 4:
                    location = "A building in a city";
                    break;
                case 5:
                case 6:
                case 7:
                case 8:
                    location = "Catacombs or sewers beneath a city";
                    break;
                case 9:
                case 10:
                case 11:
                case 12:
                    location = "Beneath a farmhouse";
                    break;
                case 13:
                case 14:
                case 15:
                case 16:
                    location = "Beneath a graveyard";
                    break;
                case 17:
                case 18:
                case 19:
                case 20:
                case 21:
                case 22:
                    location = "Beneath a ruined castle";
                    break;
                case 23:
                case 24:
                case 25:
                case 26:
                    location = "Beneath a ruined city";
                    break;
                case 27:
                case 28:
                case 29:
                case 30:
                    location = "Beneath a temple";
                    break;
                case 31:
                case 32:
                case 33:
                case 34:
                    location = "In a chasm";
                    break;
                case 35:
                case 36:
                case 37:
                case 38:
                    location = "In a cliff face";
                    break;
                case 39:
                case 40:
                case 41:
                case 42:
                    location = "In a desert";
                    break;
                case 43:
                case 44:
                case 45:
                case 46:
                    location = "In a forest";
                    break;
                case 47:
                case 48:
                case 49:
                case 50:
                    location = "In a glacier";
                    break;
                case 51:
                case 52:
                case 53:
                case 54:
                    location = "In a gorge";
                    break;
                case 55:
                case 56:
                case 57:
                case 58:
                    location = "In a jungle";
                    break;
                case 59:
                case 60:
                case 61:
                case 62:
                    location = "In a mountain pass";
                    break;
                case 63:
                case 64:
                case 65:
                case 66:
                    location = "In a swamp";
                    break;
                case 67:
                case 68:
                case 69:
                case 70:
                    location = "Beneath or inside a mesa";
                    break;
                case 71:
                case 72:
                case 73:
                case 74:
                    location = "In sea caves";
                    break;
                case 75:
                case 76:
                case 77:
                case 78:
                    location = "In several connected mesas";
                    break;
                case 79:
                case 80:
                case 81:
                case 82:
                    location = "On a mountain peak";
                    break;
                case 83:
                case 84:
                case 85:
                case 86:
                    location = "On a promontory";
                    break;
                case 87:
                case 88:
                case 89:
                case 90:
                    location = "On an island";
                    break;
                case 91:
                case 92:
                case 93:
                case 94:
                case 95:
                    location = "Underwater";
                    break;
                case 96:
                case 97:
                case 98:
                case 99:
                case 100:
                    if(allowExoticLocations)
                    {
                        location = determineExoticLocation();
                    }
                    else
                    {
                        location = "";
                        //re-roll location
                    }
                    break;
                 default:
                     location = "Location Unknown";
            }
        }

        return location;
    }

    private String determineExoticLocation()
    {
        String location;
        int roll = (int)(Math.random()*20) + 1;

        switch(roll)
        {
            case 1:
                location = "Among the branches of a tree";
                break;
            case 2:
                location = "Around a geyser";
                break;
            case 3:
                location = "Behind a waterfall";
                break;
            case 4:
                location = "Buried in an avalanche";
                break;
            case 5:
                location = "Buried in a sandstorm";
                break;
            case 6:
                location = "Buried in volcanic ash";
                break;
            case 7:
                location = "Castle or structure sunken in a swamp";
                break;
            case 8:
                location = "Castle or structure at the bottom of a sinkhole";
                break;
            case 9:
                location = "Floating on the sea";
                break;
            case 10:
                location = "In a meteorite";
                break;
            case 11:
                location = "On a demiplane or in a pocket dimension";
                break;
            case 12:
                location = "In an area devastated by a magical catastrophe";
                break;
            case 13:
                location = "On a cloud";
                break;
            case 14:
                location = "In the Feywild";
                break;
            case 15:
                location = "In the Shadowfell";
                break;
            case 16:
                location = "On an island in a underground sea";
                break;
            case 17:
                location = "In a volcano";
                break;
            case 18:
                location = "On the back of a gargantuan living creature";
                break;
            case 19:
                location = "Sealed inside a magical dome of force";
                break;
            case 20:
                location = "Inside a Mordenkainen's magnificent mansion";
                break;
            default:
                location = "The Void of Unknown Wonders";
        }

        return location;
    }

    public String determineLocationFromGivenList(String[] locationList)
    {
        int choice = (int)(Math.random() * locationList.length);
        return locationList[choice];
    }

}
