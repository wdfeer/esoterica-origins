package main

import (
	"encoding/json"
	"os"
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
	// TODO: parse origin json at `path` and powers included
}

type Power struct {
	name        string
	description string
}

func parsePower(path string) Power {
	// TODO: parse power json at `path`
}

func buildMarkdown(origins []Origin) string {
	// TODO: create a markdown file string from all the names and descriptions
}
