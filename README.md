<table>
  <tr>
    <th>master</th>
    <th>networking</th>
    <th>woody</th>
  </tr>
  <tr>
    <td>
      <a href="https://circleci.com/gh/Android-Avengers/Project-X/tree/master">
        <img alt="Build Status" src="https://circleci.com/gh/Android-Avengers/Project-X/tree/master.svg?style=shield" />
      </a>
    </td>
     <td>
      <a href="https://circleci.com/gh/Android-Avengers/Project-X/tree/networking">
        <img alt="Build Status" src="https://circleci.com/gh/Android-Avengers/Project-X/tree/networking.svg?style=shield" />
      </a>
    </td>
    <td>
      <a href="https://circleci.com/gh/Android-Avengers/Project-X/tree/woody">
        <img alt="Build Status" src="https://circleci.com/gh/Android-Avengers/Project-X/tree/woody.svg?style=shield" />
      </a>
    </td>
  </tr>
</table>

## TODO
- Cache remote values and fetch locally first then remote
- DI persistence
- Android 12 Themes
- Fix junit4 dep errors, could be due to appcompat & jetpack clashing (somewhat fixed)
- Add feature flags via GitLabs
- Slack vs Pinterest Linter

## Firebase
- Need to add ```google-services.json``` to app level directory
- Test user pass `qwerasdf`

## Gradle Tasks

### Dependency Updates
```
// From project root
// Task defined in versions.gradle
./gradlew dependencyUpdates
```

## Flutter
```
module names must be in snake_case
```

## Tools
- [PID Cat](https://github.com/JakeWharton/pidcat)

## Paths
```
// Mac
/Users/*username*/Library/Android/sdk
```

## Commands
### Shell
```
pwd | pbcopy
pbpaste
```

### Android Studio (Mac)
```
Preview Method Params: cmd + p
```

## GitHub
- Strips inline styles from md files

## CircleCI
- Most people seem to use ```android@1.0.3``` even though ```android@2.0``` is out
- Need to do some extra configurations for Firebase see [this](https://ayltai.medium.com/all-you-need-to-know-about-circleci-2-0-with-firebase-test-lab-2a66785ff3c2)
- Need to resolve issues with Flutter module
