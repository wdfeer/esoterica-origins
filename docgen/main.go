package main

import (
	"encoding/json"
	"os"
	"strings"
)

const (
	originDir  = "../src/main/resources/data/esoterica-origins/origins/"
	powerDir   = "../src/main/resources/data/esoterica-origins/powers/"
	outputFile = "./origins.md"
)

func main() {
	files, _ := os.ReadDir(originDir)

	origins := make([]Origin, len(files))
	for i, filename := range files {
		origins[i] = parseOrigin(originDir + filename.Name())
	}

	markdown := buildMarkdown(origins)
	os.WriteFile(outputFile, []byte(markdown), 0644)
}

type Origin struct {
	name        string
	description string
	powers      []Power
}

func parseOrigin(path string) Origin {
	var object map[string]any
	{
		data, _ := os.ReadFile(path)
		json.Unmarshal(data, &object)
	}

	if object["name"] == nil {
		panic("Failed reading origin at\"" + path + "\", name not found!")
	}
	if object["description"] == nil {
		panic("Failed reading origin at\"" + path + "\", description not found!")
	}
	if object["powers"] == nil {
		panic("Failed reading origin at\"" + path + "\", powers not found!")
	}

	powerNames := object["powers"].([]any)
	powers := make([]Power, len(powerNames))
	for i, identifier := range powerNames {
		path := strings.Split(identifier.(string), ":")[1]
		powers[i] = parsePower(powerDir + path)
	}

	return Origin{
		name:        object["name"].(string),
		description: object["description"].(string),
		powers:      powers,
	}
}

type Power struct {
	name        string
	description string
}

func parsePower(path string) Power {
	var object map[string]any
	{
		data, _ := os.ReadFile(path)
		json.Unmarshal(data, &object)
	}

	if object["name"] == nil {
		panic("Failed reading power at\"" + path + "\", name not found!")
	}
	if object["description"] == nil {
		panic("Failed reading power at\"" + path + "\", description not found!")
	}

	return Power{
		name:        object["name"].(string),
		description: object["description"].(string),
	}
}

func buildMarkdown(origins []Origin) string {
	var str strings.Builder
	str.WriteString("# Origins\n")
	for _, origin := range origins {
		str.WriteString("## " + origin.name + "\n")
		str.WriteString(origin.description + "\n\n")
		for _, power := range origin.powers {
			str.WriteString("- " + power.name + ": " + power.description + "\n")
		}
		str.WriteString("\n")
	}
	return str.String()
}
