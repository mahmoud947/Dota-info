import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.hero_domain.Hero
import com.example.ui_herolist.R
import com.example.ui_herolist.ui.TAG_HERO_NAME
import com.example.ui_herolist.ui.TAG_HERO_PRIMARY_ATTRIBUTE
import kotlin.math.roundToInt

@ExperimentalCoilApi
@ExperimentalAnimationApi

@Composable
fun HeroListItem(
    hero: Hero,
    onSelectHero: (Int) -> Unit,
    imageLoader: ImageLoader,
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp)
            .background(MaterialTheme.colors.surface)

            .clickable {
                onSelectHero(hero.id)
            },
        elevation = 8.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val painter = rememberImagePainter(
                data = hero.img,
                imageLoader = imageLoader,
                builder = {
                    placeholder(if (isSystemInDarkTheme()) R.drawable.black_background else R.drawable.white_background)
                }
            )
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp),
                painter = painter,
                contentDescription = hero.localizedName,
                contentScale = ContentScale.Crop

            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp, horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(.8f),

                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        modifier = Modifier
                            .padding(bottom = 4.dp)
                            .testTag(TAG_HERO_NAME),
                        text = hero.localizedName,
                        style = MaterialTheme.typography.h4,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        modifier = Modifier
                            .testTag(TAG_HERO_PRIMARY_ATTRIBUTE),
                        text = hero.primaryAttribute.uiValue,
                        style = MaterialTheme.typography.subtitle1.copy(color = Color(0xFF009a34)),
                    )
                }

                val proWR: Int =
                    (hero.proWins.toDouble() / hero.proPick.toDouble() * 100).roundToInt()

                Text(
                    text = "${proWR}%",
                    style = MaterialTheme.typography.caption,
                    color = if (proWR > 50) Color(0xFF009a34) else MaterialTheme.colors.error,
                )
            }


        }
    }
}
