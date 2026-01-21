package com.example.matatu_navigation
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.PolyUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import com.example.matatu_navigation.BuildConfig

object DirectionsRepository {
    private val client = OkHttpClient()

    suspend fun getRoute(origin: String, destination: String): RouteResult = withContext(Dispatchers.IO) {
        // Use BuildConfig.MAPS_API_KEY (setup in build.gradle) for better management
        val apiKey = BuildConfig.MAPS_API_KEY
        val originEscaped = java.net.URLEncoder.encode(origin, "utf-8")
        val destEscaped = java.net.URLEncoder.encode(destination, "utf-8")

        val url = "https://maps.googleapis.com/maps/api/directions/json?" +
                "origin=$originEscaped&destination=$destEscaped&mode=driving&key=$apiKey"

        val req = Request.Builder().url(url).build()
        client.newCall(req).execute().use { resp ->
            if (!resp.isSuccessful) throw Exception("Network error: ${resp.code}")
            val body = resp.body?.string() ?: throw Exception("Empty response")
            val root = JSONObject(body)
            val routes = root.optJSONArray("routes")
            if (routes == null || routes.length() == 0) throw Exception("No routes found")

            val r0 = routes.getJSONObject(0)
            val overview = r0.getJSONObject("overview_polyline").getString("points")
            val points = PolyUtil.decode(overview).map { LatLng(it.latitude, it.longitude) } // maps-utils type compatibility

            // legs -> distance/duration + steps
            val legs = r0.getJSONArray("legs")
            val leg0 = legs.getJSONObject(0)
            val distanceText = leg0.getJSONObject("distance").getString("text")
            val durationText = leg0.getJSONObject("duration").getString("text")

            val stepsArr = leg0.getJSONArray("steps")
            val steps = mutableListOf<RouteStep>()
            for (i in 0 until stepsArr.length()) {
                val s = stepsArr.getJSONObject(i)
                val instrHtml = s.getString("html_instructions")
                // remove html tags (simple)
                val instr = instrHtml.replace(Regex("<.*?>"), "")
                val stepDist = s.getJSONObject("distance").getString("text")
                val stepDur = s.getJSONObject("duration").getString("text")
                steps.add(RouteStep(instr, stepDist, stepDur))
            }

            RouteResult(points, distanceText, durationText, steps)
        }
    }
}

data class RouteResult(
    val polylinePoints: List<com.google.android.gms.maps.model.LatLng>,
    val distanceText: String,
    val durationText: String,
    val steps: List<RouteStep>
)

data class RouteStep(
    val instruction: String,
    val distanceText: String,
    val durationText: String
)
