package com.cuanticosoluciones.superheroesx

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.Spring.DampingRatioLowBouncy
import androidx.compose.animation.core.Spring.StiffnessVeryLow
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cuanticosoluciones.superheroesx.model.Hero
import com.cuanticosoluciones.superheroesx.data.HeroesRepository
import com.cuanticosoluciones.superheroesx.ui.theme.SuperHeroesXTheme


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun HeroesList(
    modifier: Modifier = Modifier,
    heroes: List<Hero>,
    contentPadding: PaddingValues = PaddingValues(16.dp)
) {
    val visibleState = remember {
        MutableTransitionState(false)
            .apply { targetState = true }
    }

    AnimatedVisibility(
        modifier = modifier,
        visibleState = visibleState,
        enter = fadeIn(
            animationSpec = spring(
                dampingRatio = DampingRatioLowBouncy,
            ),
        ),
        exit = fadeOut()
    ) {
        LazyColumn(contentPadding = contentPadding) {
            itemsIndexed(heroes) { index, hero ->
                HeroListItem(
                    modifier = Modifier
                        .padding(
                            horizontal = 16.dp,
                            vertical = 8.dp
                        )
                        .animateEnterExit(
                            enter = slideInVertically(
                                animationSpec = spring(
                                    stiffness = StiffnessVeryLow,
                                    dampingRatio = DampingRatioLowBouncy
                                ),
                                initialOffsetY = { it * (index + 1) }
                            )
                        ),
                    hero = hero
                )
            }

        }
    }
}

@Composable
fun HeroListItem(
    modifier: Modifier = Modifier,
    hero: Hero
) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .sizeIn(minHeight = 72.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = stringResource(hero.nameRes),
                    style = MaterialTheme.typography.displaySmall
                )
                Text(
                    text = stringResource(hero.descriptionRes),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Spacer(Modifier.width(16.dp))
            Box(
                modifier = Modifier
                    .size(72.dp)
                    .clip(RoundedCornerShape(8.dp))

            ) {
                Image(
                    painter = painterResource(hero.imageRes),
                    contentDescription = null,
                    alignment = Alignment.TopCenter,
                    contentScale = ContentScale.FillWidth
                )
            }
        }
    }
}

@Preview("Light Theme")
@Preview("Dark Theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun HeroPreview() {
    val hero = Hero(
        R.string.hero1,
        R.string.description1,
        R.drawable.android_superhero1
    )

    SuperHeroesXTheme {
        HeroListItem(hero = hero)
    }
}

@Preview
@Composable
fun HeroesPreview() {
    SuperHeroesXTheme(darkTheme = false) {
        Surface (
            color = MaterialTheme.colorScheme.background
        ) {
            HeroesList(
                heroes = HeroesRepository.heroes
            )
        }
    }
}