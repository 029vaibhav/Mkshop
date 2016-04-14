package com.mobiles.mkshop.fragments;

public class Attendance { /*extends Fragment implements com.google.android.gms.location.LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    // TODO: Rename parameter arguments, choose names that match


    SharedPreferences sharedPreferences;

    protected static final int REQUEST_CHECK_SETTINGS = 0x1;
    public static String TAG = "Attendance";

    TextView attendance;
    GoogleApiClient googleApiClient;
    LocationRequest locationRequest;
    LoginDetails loginDetailsList;
    ProgressDialog materialDialog;
    Location startLocation;
    boolean isTempSet = false;
    Location tempLocation;


    public static Attendance newInstance() {
        Attendance fragment = new Attendance();
        return fragment;

    }

    public Attendance() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        MkShop.SCRREN = "Attendance";
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_attendance, container, false);
        attendance = (TextView) viewGroup.findViewById(R.id.attendancetext);


        sharedPreferences = getActivity().getSharedPreferences("MKSHOP", Context.MODE_PRIVATE);
        String json = sharedPreferences.getString("DETAIL", null);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            loginDetailsList = objectMapper.readValue(json, LoginDetails.class);
        } catch (IOException e) {
            e.printStackTrace();
        }


        materialDialog = NavigationMenuActivity.materialDialog;


        googleApiClient = new GoogleApiClient.Builder(getActivity())
                .addApi(LocationServices.API)
                .addConnectionCallbacks((this))
                .addOnConnectionFailedListener(this).build();
        googleApiClient.connect();


        attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                showSettingsAlert();

            }
        });


        return viewGroup;
    }


    private void showSettingsAlert() {
        // TODO Auto-generated method stub


        // Sets the fastest rate for active location updates. This interval is exact, and your
        // application will never receive updates faster than this value.


        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(30 * 1000);
        locationRequest.setFastestInterval(5 * 1000);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);


        builder.setAlwaysShow(true);

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {

            @Override
            public void onResult(LocationSettingsResult result) {
                // TODO Auto-generated method stub
                final Status status = result.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        startLocationUpdates();
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:

                        try {
                            // Show the dialog by calling startResolutionForResult(), and check the result
                            // in onActivityResult().
                            Log.e("RESOLUTION_REQUIRED", "REQUEST_CHECK_SETTINGS");
                            status.startResolutionForResult(getActivity(), REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            Log.i("dfd", "PendingIntent unable to execute request.");
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        Log.i("fdfd", "Location settings are inadequate, and cannot be fixed here. Dialog " +
                                "not created.");
                        break;
                }
            }
        });


    }


    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }


    protected void startLocationUpdates() {


        materialDialog.show();

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(
                googleApiClient,
                locationRequest,
                this
        ).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(Status status) {
                markAttendance();

            }
        });

    }

    private void markAttendance() {


        if (loginDetailsList.getLocation() == null) {
            MkShop.toast(getActivity(), "Ask your admin to provide your location");
            if (materialDialog != null && materialDialog.isShowing())
                materialDialog.dismiss();
            return;
        }

        if (startLocation == null) {
            if (materialDialog != null && materialDialog.isShowing())
                materialDialog.dismiss();
            MkShop.toast(getActivity(), "Please try after 10 secs while we fetch your location");
            return;
        }


        Location end = new Location("B");
        end.setLatitude(Double.parseDouble(loginDetailsList.getLocation().getLatitude()));
        end.setLongitude(Double.parseDouble(loginDetailsList.getLocation().getLongitude()));
        double distance = startLocation.distanceTo(end);


        Log.e("locationm", "gps" + startLocation.getLatitude() + "gps-lon" + startLocation.getLongitude() + "dis" + distance);
        if (distance <= 100 + (Integer.parseInt(loginDetailsList.getLocation().getRadius()))) {

            Call<String> stringCall = Client.INSTANCE.markAttendance(MkShop.AUTH, MkShop.Username);
            stringCall.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {

                    if (materialDialog != null && materialDialog.isShowing())
                        materialDialog.dismiss();
                    MkShop.toast(getActivity(), response.body());
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    if (materialDialog != null && materialDialog.isShowing())
                        materialDialog.dismiss();
                    MkShop.toast(getActivity(), "your are not in the coverage area");
                }
            });

        } else {

        }


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            // Check for the integer request code originally supplied to startResolutionForResult().
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        startLocationUpdates();
                        break;
                    case Activity.RESULT_CANCELED:
                        Log.i("RESULT_CANCELED", "User chose not to make required location settings changes.");
                        MkShop.toast(getActivity(), "please turn on the gps to mark your attendance");

                        break;
                }
                break;
        }
    }

    @Override
    public void onLocationChanged(Location location) {

        startLocation = location;

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }*/
}
