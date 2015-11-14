package com.smartplace.polar.helpers;

import android.os.Handler;

import com.smartplace.polar.models.Feature;
import com.smartplace.polar.models.Specification;
import com.smartplace.polar.models.Link;
import com.smartplace.polar.models.LinkReference;
import com.smartplace.polar.models.Requirement;
import com.smartplace.polar.models.Team;

import java.util.ArrayList;

/**
 * Created by robertoreym on 01/11/15.
 */
public class WebServices {

    public static void getUserTeam(String publicKey, String teamID, final OnUserTeamListener listener){

        final Team team = new Team();
        team.setId(teamID);
        team.setName("Smartplace");
        team.setSpecifications(new ArrayList<Specification>());

        for (int i = 0; i<3;i++){

            Specification specification = new Specification();
            specification.setId(String.format("%d",i+1));
            specification.setName("Specification " + String.valueOf(i+1));
            specification.setFeatures(new ArrayList<Feature>());

            team.getSpecifications().add(specification);

        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                listener.onUserTeamReceived(team);
            }
        },2000);
    }

    public static void getTeamSpecification(String publicKey, String specificationID, final OnTeamFileListener listener){

        final Specification specification = new Specification();
        specification.setId(specificationID);
        specification.setName("Specification " + specificationID);
        specification.setFeatures(new ArrayList<Feature>());
        //set
        for(int i = 0 ; i<10;i++){

            Feature feature = new Feature();
            feature.setId(String.format("%d", i + 1));
            feature.setName(String.format("#Feature %s%d", specificationID, i + 1));
            specification.getFeatures().add(feature);
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                listener.onTeamFileReceived(specification);
            }
        },2000);
    }

    public static void getSpecificationFeature(String publicKey, String featureID, final OnFileFeatureListener listener){

        final Feature feature = new Feature();
        feature.setId(featureID);
        feature.setName("#Feature "+featureID);
        feature.setRequirements(new ArrayList<Requirement>());

        //set requirements
        for(int i = 0 ; i<30;i++){

            Requirement requirement = new Requirement();
            requirement.setId(String.valueOf(i + 1));
            requirement.setOrder(i + 1);
            requirement.setType(1);
            requirement.setValue(String.valueOf(i+1) +" Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
            requirement.setInLinks(new ArrayList<Link>());
            requirement.setOutLinks(new ArrayList<Link>());

            if(i%3 == 0) {

                //set links
                for (int i2 = 0; i2 < 3; i2++) {

                    Link link = new Link();
                    link.setId(String.valueOf(i2 + 1));


                    LinkReference linkReference = new LinkReference();
                    linkReference.setFileID("2");
                    linkReference.setFileName("File 2");
                    linkReference.setFeatureID("2");
                    linkReference.setFeatureName("Feature 2");
                    linkReference.setRequirementID(requirement.getId() + 1);
                    linkReference.setRequirementType(requirement.getType() + 1);
                    linkReference.setRequirementValue("Other " + requirement.getValue());

                    link.setReference(linkReference);

                    if(i2%2 == 0){

                        //link in
                        requirement.getInLinks().add(link);
                    }else{

                        //link out
                        requirement.getOutLinks().add(link);

                    }

                }
            }

            feature.getRequirements().add(requirement);
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                listener.onFileFeatureReceived(feature);
            }
        },2000);
    }
    public interface OnUserTeamListener{

        void onUserTeamReceived(Team team);
    }
    public interface OnTeamFileListener{

        void onTeamFileReceived(Specification specification);
    }
    public interface OnFileFeatureListener{

        void onFileFeatureReceived(Feature feature);
    }


}
