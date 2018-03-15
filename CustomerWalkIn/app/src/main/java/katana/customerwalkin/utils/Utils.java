package katana.customerwalkin.utils;

public class Utils {

//    public static boolean isDeviceOnline(Context context) {
//        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
//        boolean isOnline = (networkInfo != null && networkInfo.isConnected());
//        if (!isOnline) {
//            Toast.makeText(context, context.getString(R.string.txt_check_network), Toast.LENGTH_SHORT).show();
//        }
//        return isOnline;
//    }
//
//    public static boolean checkNumber(String str) {
//        Pattern pNumber = Pattern.compile("[0-9]");
//        Matcher mNumber = pNumber.matcher(str);
//        return mNumber.find();
//    }
//
//    public static void openGalerry(Activity context, int requestCode) {
//        Intent localIntent = new Intent("android.intent.action.PICK");
//        localIntent.setType("image/*");
//        context.startActivityForResult(localIntent, requestCode);
//    }
//
//    public static String getCostFormat(String cost) {
//        if (cost == null || cost.equals("") || cost.equals("null")) {
//            return "";
//        } else {
//            Locale locale = new Locale("en", "UK");
//            DecimalFormatSymbols symbols = new DecimalFormatSymbols(locale);
//            symbols.setDecimalSeparator('.');
//            symbols.setGroupingSeparator(',');
//            String pattern = "#,##0.00";
//            DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
//            return decimalFormat.format(new BigDecimal(cost));
//        }
//    }
//
//    public static String getDistanceFormat(String km) {
//        if (km == null || km.equals("") || km.equals("null")) {
//            return "";
//        } else {
//            Locale locale = new Locale("en", "UK");
//            DecimalFormatSymbols symbols = new DecimalFormatSymbols(locale);
////            symbols.setDecimalSeparator('.');
//            symbols.setGroupingSeparator(',');
//            String pattern = "#,###";
//            DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
//            return decimalFormat.format(new BigDecimal(km));
//        }
//    }
//
//    public static boolean checkGpsEnable(Context context) {
//        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
//        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
//    }
//
//    public static String getDateDDMMMYYYY(Date date) {
//        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
//        return df.format(date);
//    }
//
//    public static String getTime(Date date) {
//        SimpleDateFormat df = new SimpleDateFormat("HH:mm", Locale.getDefault());
//        return df.format(date);
//    }
//
//    public static String formatServerTime(Date date) {
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault());
//        return df.format(date);
//    }
//
//    public static void openDialAndroid(Context context, String phone) {
//        try {
//            Intent intent = new Intent(Intent.ACTION_DIAL);
//            StringBuilder builder = new StringBuilder();
//            builder.append("tel:");
//            builder.append(phone);
//            intent.setData(Uri.parse(builder.toString()));
//            context.startActivity(intent);
//        } catch (ActivityNotFoundException e) {
//            Toast.makeText(context, context.getString(R.string.txt_not_dial), Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    public static void setListViewHeightBasedOnChildren(ListView listView) {
//
//        ListAdapter listAdapter = listView.getAdapter();
//
//        if (listAdapter == null) {
//            // pre-condition
//            return;
//        }
//
//        int totalHeight = 0;
//
//        for (int i = 0; i < listAdapter.getCount(); i++) {
//            View listItem = listAdapter.getView(i, null, listView);
//            listItem.measure(0, 0);
//            totalHeight += listItem.getMeasuredHeight();
//        }
//        ViewGroup.LayoutParams params = listView.getLayoutParams();
//        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
//        listView.setLayoutParams(params);
//        listView.requestLayout();
//    }
//
//    public static String intervalFromPost(Context context, Date date) {
//        if (date != null) {
//            long createTime = date.getTime();
//            long currentTime = System.currentTimeMillis();
//            long detal = (currentTime - createTime) / 1000;
//            if (detal >= 0) {
//                int dayNumber = (int) (detal / 86400);
//                if (dayNumber > 0) {
//                    return String.format(context.getString(R.string.txt_interval_post_date), String.valueOf(dayNumber));
//                } else {
//                    return context.getString(R.string.txt_today);
////                    calendar.setTimeInMillis(currentTime);
////                    int hour = calendar.get(Calendar.HOUR_OF_DAY);
////                    int minute = calendar.get(Calendar.MINUTE);
////                    if (hour * 60 + minute < Integer.valueOf(timeArray[0]) * 60 + Integer.valueOf(timeArray[1])) {
////                        return context.getString(R.string.txt_today);
////                    } else {
////                        return context.getString(R.string.txt_running_trip);
////                    }
//                }
//            }
//        }
//        return "";
//    }
}
