package jp.techacademy.katsuhito.muratomi.mapapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PointOfInterest;

import static jp.techacademy.katsuhito.muratomi.mapapp.R.id.map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback  {

    private GoogleMap mMap;
    String[] string = {"Manifest.permission.WRITE_EXTERNAL_STORAGE",
            "Manifest.permission.ACCESS_FINE_LOCATION",
    };
    int status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(map);
        mapFragment.getMapAsync(this);




      /*  if (status == ConnectionResult.SUCCESS) {
            int PLACE_PICKER_REQUEST = 199;
            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
            Context context = this;
            try {
                startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);
            } catch (GooglePlayServicesRepairableException e) {
                e.printStackTrace();
            } catch (GooglePlayServicesNotAvailableException e) {
                e.printStackTrace();
            }
        } */


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (
                    checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                    checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ) {
Log.d("test","ok");
            } else {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.ACCESS_FINE_LOCATION}, 1);

            }
        }

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.setMyLocationEnabled(true);
        mMap.setOnPoiClickListener(new GoogleMap.OnPoiClickListener() {
            @Override
            public void onPoiClick(PointOfInterest poi) {
                Toast.makeText(MapsActivity.this, "Clicked: " +
                                poi.name + "\nPlace ID:" + poi.placeId +
                                "\nLatitude:" + poi.latLng.latitude +
                                " Longitude:" + poi.latLng.longitude,
                        Toast.LENGTH_LONG).show();

                LatLng point =new LatLng(poi.latLng.latitude,poi.latLng.longitude);
                mMap.moveCamera(CameraUpdateFactory.newLatLng(point));
                mMap.addMarker(new MarkerOptions().position(point).title("移動したよ"));
                //try catch しないとエラーがでてしまう

                try{
                    int request =1;
                    PlacePicker.IntentBuilder builder =new PlacePicker.IntentBuilder();
                    Intent intent=builder.build(MapsActivity.this);
                    startActivityForResult(intent,request);
                }catch (GooglePlayServicesNotAvailableException e) {

                }catch (GooglePlayServicesRepairableException e){

                }


            }


        });




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //PlacePickerの画面から戻るときに呼ばれている
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

    }


}
